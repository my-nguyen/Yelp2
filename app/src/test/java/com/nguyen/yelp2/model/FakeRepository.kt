package com.nguyen.yelp2.model

import com.nguyen.yelp2.model.json.Business
import com.nguyen.yelp2.model.json.Category
import com.nguyen.yelp2.model.json.Data
import com.nguyen.yelp2.model.json.Location

class FakeRepository : Repository {
    override suspend fun searchBusinesses(term: String, location: String): Data {
        val business = Business(
            categories = listOf(Category(title = "Burgers")),
            distance = 4972.219081884518,
            image_url = "https://s3-media3.fl.yelpcdn.com/bphoto/nITulRo9W-sbUqdC6NtmcQ/o.jpg",
            location = Location(address1 = "550 Newhall Dr"),
            name = "In-N-Out Burger",
            price = "$",
            rating = 4.0,
            review_count = 644
        )
        return Data(businesses = listOf(business))
    }
}