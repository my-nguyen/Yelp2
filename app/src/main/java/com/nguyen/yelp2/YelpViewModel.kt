package com.nguyen.yelp2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val API_KEY = "7WnY3zVPZ8Yj9S8JWphAu5dn8myhk0N0eAZ4P5vluMBcEg7t1gc41fdBHgluTHLNziDGBiH0UvciG4-p-IJQfPvR5Pdhd9WJ1G4pQnwZr6_cZG54KU6rZVrjITSfX3Yx"

class YelpViewModel: ViewModel() {
    val data = MutableLiveData<Data>()

    fun searchBusinesses(term: String, location: String) {
        CoroutineScope(IO).launch {
            val result = YelpApi.service.searchBusinesses("Bearer $API_KEY", term, location)
            withContext(Main) {
                data.postValue(result)
            }
        }
    }
}