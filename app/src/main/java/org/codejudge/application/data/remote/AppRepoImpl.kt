package org.codejudge.application.data.remote

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

}