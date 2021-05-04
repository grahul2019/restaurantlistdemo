package org.codejudge.application.data.mappers

import org.codejudge.application.data.model.Photo
import org.codejudge.application.data.model.RestaurantListRemoteResponse
import org.codejudge.application.data.model.RestaurantRemoteData
import org.codejudge.application.domain.model.RestaurantImageParams
import org.codejudge.application.domain.model.RestaurantListResModel
import org.codejudge.application.utils.Mapper
import javax.inject.Inject

class RestaurantListMapper @Inject constructor() :
    Mapper<List<RestaurantListResModel>, RestaurantListRemoteResponse> {

    override fun mapEntity(model: RestaurantListRemoteResponse): List<RestaurantListResModel> {

        fun toRestaurantImageParams(photo: Photo?): RestaurantImageParams {
            return RestaurantImageParams(
                photoReference = photo?.photoReference,
                height = photo?.height,
                width = photo?.width
            )
        }

        fun toRestaurantData(restaurantRemoteData: RestaurantRemoteData): RestaurantListResModel {
            return RestaurantListResModel(
                restaurantName = restaurantRemoteData?.name,
                restaurantImageParams = toRestaurantImageParams(restaurantRemoteData.photos?.get(0)),
                restaurantRating = restaurantRemoteData?.rating,
                isOpened = restaurantRemoteData?.openingHours?.openNow
            )
        }

        val listOfRestaurants = arrayListOf<RestaurantListResModel>()
        model.results?.forEach { restaurantRemoteData ->
            listOfRestaurants.add(toRestaurantData(restaurantRemoteData))
        }
        return listOfRestaurants
    }

}