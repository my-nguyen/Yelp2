package com.nguyen.yelp2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nguyen.yelp2.model.Repository
import com.nguyen.yelp2.model.json.Data
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class YelpViewModel(private val repository: Repository): ViewModel() {
    val data = MutableLiveData<Data>()

    fun searchBusinesses(term: String, location: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.searchBusinesses(term, location)
            withContext(Dispatchers.Main) {
                data.postValue(result)
            }
        }
    }
}

class YelpViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(YelpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return YelpViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}