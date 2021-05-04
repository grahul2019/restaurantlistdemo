package org.codejudge.application.data.remote

import org.codejudge.application.BuildConfig
import org.codejudge.application.data.mappers.RestaurantListMapper
import org.codejudge.application.domain.model.RestaurantListResModel
import javax.inject.Inject

class AppRepoImpl @Inject constructor(
    private val remoteAPIService: AppRemoteAPIService,
    private val restaurantListMapper: RestaurantListMapper
): AppRepo {

    override suspend fun getRestaurantList(): List<RestaurantListResModel> {
        return restaurantListMapper.mapEntity(remoteAPIService.getRestaurantList())
    }

    override suspend fun searchRestaurants(searchQuery: String): List<RestaurantListResModel> {
        fun makeUrl():String{
            return  "${BuildConfig.APP_URL}nearbysearch/json?location=47.6204,-122.3491&radius=2500&type=restaurant&keyword=:${searchQuery}&key=${BuildConfig.API_KEY}"
        }
        return restaurantListMapper.mapEntity(remoteAPIService.searchRestaurant(makeUrl()))
    }

}