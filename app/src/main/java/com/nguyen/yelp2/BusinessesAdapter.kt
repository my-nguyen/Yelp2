package com.nguyen.yelp2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyen.yelp2.databinding.ItemBusinessBinding

class BusinessesAdapter(private val businesses: List<Business>): RecyclerView.Adapter<BusinessesAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemBusinessBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(business: Business) {
            binding.name.text = business.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBusinessBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = businesses.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(businesses[position])
}