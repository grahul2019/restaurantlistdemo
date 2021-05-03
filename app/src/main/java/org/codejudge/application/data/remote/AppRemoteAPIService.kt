package org.codejudge.application.data.remote

import org.codejudge.application.BuildConfig
import org.codejudge.application.data.model.RestaurantListRemoteResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AppRemoteAPIService {

    @GET("nearbysearch/json?location=47.6204,-122.3491&radius=2500&type=restaurant&key=${BuildConfig.API_KEY}")
    suspend fun getRestaurantList(): RestaurantListRemoteResponse

    @GET("nearbysearch/json?location=47.6204,-122.3491&radius=2500&type=restaurant&keyword=:{searchQuery}&key=${BuildConfig.API_KEY}")
    suspend fun searchRestaurant(@Path("searchQuery")searchQuery:String): RestaurantListRemoteResponse
}