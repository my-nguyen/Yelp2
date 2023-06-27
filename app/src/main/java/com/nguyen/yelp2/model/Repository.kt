package com.nguyen.yelp2.model

import com.nguyen.yelp2.model.json.Data

interface Repository {
    suspend fun searchBusinesses(term: String, location: String): Data
}