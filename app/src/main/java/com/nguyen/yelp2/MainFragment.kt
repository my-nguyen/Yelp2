package com.nguyen.yelp2

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyen.yelp2.databinding.FragmentMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val BASE_URL = "https://api.yelp.com/v3/"
private const val TAG = "MainActivity"
private const val API_KEY = "7WnY3zVPZ8Yj9S8JWphAu5dn8myhk0N0eAZ4P5vluMBcEg7t1gc41fdBHgluTHLNziDGBiH0UvciG4-p-IJQfPvR5Pdhd9WJ1G4pQnwZr6_cZG54KU6rZVrjITSfX3Yx"

class MainFragment : Fragment(R.layout.fragment_main) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMainBinding.bind(view)
        val businesses = mutableListOf<Business>()
        val adaptor = BusinessesAdapter(businesses)
        binding.recycler.apply {
            adapter = adaptor
            layoutManager = LinearLayoutManager(requireContext())
        }

        YelpApi.service.searchBusinesses("Bearer $API_KEY", "Avocado Toast", "New York").enqueue(object:
            Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                Log.i(TAG, "onResponse $response")
                val body = response.body()
                if (body == null) {
                    Log.w(TAG, "Did not receive valid response body from Yelp API... exiting")
                    return
                }
                businesses.addAll(body.businesses)
                adaptor.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }
        })
    }
}