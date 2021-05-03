package org.codejudge.application.data.remote

import org.codejudge.application.domain.model.RestaurantListResModel

interface AppRepo{
    suspend fun getRestaurantList():List<RestaurantListResModel>
}