package com.nguyen.yelp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.yelp.com/v3/"
private const val TAG = "MainActivity"
private const val API_KEY = "7WnY3zVPZ8Yj9S8JWphAu5dn8myhk0N0eAZ4P5vluMBcEg7t1gc41fdBHgluTHLNziDGBiH0UvciG4-p-IJQfPvR5Pdhd9WJ1G4pQnwZr6_cZG54KU6rZVrjITSfX3Yx"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(YelpService::class.java)
        service.searchBusinesses("Bearer $API_KEY", "Avocado Toast", "New York").enqueue(object: Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                Log.i(TAG, "onResponse $response")
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }
        })
    }
}