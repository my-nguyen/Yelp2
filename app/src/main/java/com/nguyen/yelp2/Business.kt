package com.nguyen.yelp2

data class Business(
    val alias: String,
    val categories: List<Category>,
    val coordinates: Coordinates,
    val display_phone: String,
    val distance: Double,
    val id: String,
    val image_url: String,
    val is_closed: Boolean,
    val location: Location,
    val name: String,
    val phone: String,
    val price: String,
    val rating: Double,
    val review_count: Int,
    val transactions: List<String>,
    val url: String
) {
    fun toMiles(): String {
        val perMeter = 0.000621371
        val miles = "%.2f".format(distance * perMeter)
        return "$miles mi"
    }
}