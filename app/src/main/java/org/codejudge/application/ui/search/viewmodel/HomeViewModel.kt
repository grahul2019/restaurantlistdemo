package org.codejudge.application.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.codejudge.application.data.remote.AppRepo
import org.codejudge.application.domain.model.RestaurantListResModel
import org.codejudge.application.ui.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.codejudge.application.utils.ResultStatus
import org.codejudge.application.utils.Status
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val appRepo: AppRepo
): BaseViewModel() {


    private var _mRestaurantListData = MutableLiveData<ResultStatus<List<RestaurantListResModel>>>()
    val mRestaurantList:LiveData<ResultStatus<List<RestaurantListResModel>>>  = _mRestaurantListData

    fun getRestaurantList() {
        ioScope.launch {
            withContext(ioScope.coroutineContext){
                _mRestaurantListData?.postValue(ResultStatus(Status.LOADING))
                appRepo.getRestaurantList()
            }?.let { restaurantList ->
               if (restaurantList.isNotEmpty()){
                   _mRestaurantListData?.postValue(ResultStatus(Status.SUCCESS,restaurantList))
               }else{
                   _mRestaurantListData?.postValue(ResultStatus(Status.EMPTY,restaurantList))
               }?: kotlin.run {

               }
                return@let
            }
        }
    }

    fun searchRestaurant(searchQuery: String) {
       if(searchQuery.trim().isEmpty()){
           getRestaurantList()
       }else{
           ioScope.launch {
               withContext(ioScope.coroutineContext) {
                   _mRestaurantListData?.postValue(ResultStatus(Status.LOADING))
                   appRepo.searchRestaurants(searchQuery)
               }?.let { restaurantList ->
                   if (restaurantList.isNotEmpty()) {
                       _mRestaurantListData?.postValue(ResultStatus(Status.SUCCESS, restaurantList))
                   } else {
                       _mRestaurantListData?.postValue(ResultStatus(Status.EMPTY, restaurantList))
                   } ?: kotlin.run {

                   }
                   return@let
               }
           }
       }
    }
}