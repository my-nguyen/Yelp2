package com.nguyen.yelp2.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.nguyen.yelp2.databinding.ItemBusinessBinding
import com.nguyen.yelp2.model.json.Business

class BusinessesAdapter(private val businesses: List<Business>): RecyclerView.Adapter<BusinessesAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemBusinessBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(business: Business) {
            binding.apply {
                name.text = business.name
                rating.rating = business.rating.toFloat()
                reviews.text = "${business.review_count} Reviews"
                address.text = business.location.address1
                category.text = business.categories[0].title
                distance.text = business.toMiles()
                price.text = business.price
                val options = RequestOptions().transforms(CenterCrop(), RoundedCorners(20))
                Glide.with(itemView).load(business.image_url).apply(options).into(image)
            }
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