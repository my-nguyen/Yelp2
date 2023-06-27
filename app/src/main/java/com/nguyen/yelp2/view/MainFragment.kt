package com.nguyen.yelp2.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyen.yelp2.model.json.Business
import com.nguyen.yelp2.model.BusinessesAdapter
import com.nguyen.yelp2.R
import com.nguyen.yelp2.viewmodel.YelpViewModel
import com.nguyen.yelp2.databinding.FragmentMainBinding

private const val TAG = "MainActivity"
private const val API_KEY = "7WnY3zVPZ8Yj9S8JWphAu5dn8myhk0N0eAZ4P5vluMBcEg7t1gc41fdBHgluTHLNziDGBiH0UvciG4-p-IJQfPvR5Pdhd9WJ1G4pQnwZr6_cZG54KU6rZVrjITSfX3Yx"

class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel by viewModels<YelpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMainBinding.bind(view)
        val businesses = mutableListOf<Business>()
        val adaptor = BusinessesAdapter(businesses)
        binding.recycler.apply {
            adapter = adaptor
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.data.observe(viewLifecycleOwner) { data ->
            businesses.addAll(data.businesses)
            adaptor.notifyDataSetChanged()
        }

        viewModel.searchBusinesses("Avocado Toast", "New York")
    }
}