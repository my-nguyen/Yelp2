package com.nguyen.yelp2.model.json

data class Data(
    val businesses: List<Business>,
    val region: Region,
    val total: Int
)