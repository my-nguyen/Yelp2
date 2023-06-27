package com.nguyen.yelp2.model

import com.nguyen.yelp2.model.json.Data
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YelpService {
    @GET("businesses/search")
    suspend fun searchBusinesses(@Header("Authorization") authorization: String, @Query("term") term: String, @Query("location") location: String): Data
}