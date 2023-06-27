package com.nguyen.yelp2.model

import com.nguyen.yelp2.model.json.Data
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY = "7WnY3zVPZ8Yj9S8JWphAu5dn8myhk0N0eAZ4P5vluMBcEg7t1gc41fdBHgluTHLNziDGBiH0UvciG4-p-IJQfPvR5Pdhd9WJ1G4pQnwZr6_cZG54KU6rZVrjITSfX3Yx"

class RemoteRepository: Repository {
    private val service: YelpService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        retrofit.create(YelpService::class.java)
    }

    override suspend fun searchBusinesses(term: String, location: String) = service.searchBusinesses("Bearer $API_KEY", term, location)
}