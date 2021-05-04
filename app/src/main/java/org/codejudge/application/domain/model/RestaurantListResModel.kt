package org.codejudge.application.domain.model


data class RestaurantListResModel(
    val restaurantName:String?,
    val restaurantImageParams: RestaurantImageParams?,
    val restaurantRating:Double?,
    val isOpened:Boolean? =false
)

data class RestaurantImageParams(
    val height: Int?,
    val width: Int?,
    val photoReference: String?
)