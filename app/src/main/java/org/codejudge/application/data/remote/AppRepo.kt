package org.codejudge.application.data.remote

import org.codejudge.application.domain.model.RestaurantListResModel

interface AppRepo{
    suspend fun getRestaurantList():List<RestaurantListResModel>
    suspend fun searchRestaurants(searchQuery: String): List<RestaurantListResModel>
}