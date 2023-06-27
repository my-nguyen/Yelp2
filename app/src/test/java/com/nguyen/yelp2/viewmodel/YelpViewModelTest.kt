package com.nguyen.yelp2.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nguyen.yelp2.model.FakeRepository
import com.nguyen.yelp2.model.json.Category
import com.nguyen.yelp2.model.json.Location
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class YelpViewModelTest {
    // JUnit rule that configures LiveData to execute each task synchronously
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    // custom rule that configures Dispatchers.Main to use a TestCoroutineDispatcher from
    // kotlinx-coroutines-test. This allows tests to advance a virtual-clock for testing, and allows
    // code to use Dispatchers.Main in unit tests.
    // need this rule to get past the error "java.util.concurrent.TimeoutException: LiveData value was never set."
    @get:Rule
    val coroutineScope =  MainCoroutineScopeRule()

    @Test
    fun getData() = runTest {
        val viewModel = YelpViewModel(FakeRepository())
        viewModel.searchBusinesses("In-n-Out", "San Jose")
        val record = viewModel.data.getOrAwaitValue()
        assertThat(record, not(nullValue()))
        assertThat(record.businesses.size, equalTo(1))
        val business = record.businesses[0]
        assertThat(business.categories, equalTo(listOf(Category(title = "Burgers"))))
        assertThat(business.distance, equalTo(4972.219081884518))
        assertThat(business.image_url, equalTo("https://s3-media3.fl.yelpcdn.com/bphoto/nITulRo9W-sbUqdC6NtmcQ/o.jpg"))
        assertThat(business.location, equalTo(Location(address1 = "550 Newhall Dr")))
        assertThat(business.name, equalTo("In-N-Out Burger"))
        assertThat(business.price, equalTo("$"))
        assertThat(business.rating, equalTo(4.0))
        assertThat(business.review_count, equalTo(644))
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
        afterObserve: () -> Unit = {}
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }
        this.observeForever(observer)

        try {
            afterObserve.invoke()

            // Don't wait indefinitely if the LiveData is not set.
            if (!latch.await(time, timeUnit)) {
                throw TimeoutException("LiveData value was never set.")
            }

        } finally {
            this.removeObserver(observer)
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }
}