package com.nguyen.yelp2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyen.yelp2.databinding.FragmentMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        CoroutineScope(IO).launch {
            val data = YelpApi.service.searchBusinesses("Bearer $API_KEY", "Avocado Toast", "New York")
            businesses.addAll(data.businesses)
            withContext(Main) {
                adaptor.notifyDataSetChanged()
            }
        }
    }
}