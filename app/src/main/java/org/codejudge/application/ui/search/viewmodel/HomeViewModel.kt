package org.codejudge.application.ui.search.viewmodel

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import org.codejudge.application.data.remote.AppRepo
import org.codejudge.application.domain.model.RestaurantListResModel
import org.codejudge.application.ui.base.viewmodel.BaseViewModel
import org.codejudge.application.utils.ResultStatus
import org.codejudge.application.utils.Status
import java.net.UnknownHostException
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val appRepo: AppRepo,
) : BaseViewModel() {

    private var _mRestaurantListData = MutableLiveData<ResultStatus<List<RestaurantListResModel>>>()
    val mRestaurantList: LiveData<ResultStatus<List<RestaurantListResModel>>> = _mRestaurantListData

    fun getRestaurantList() {
        ioScope.launch {
            try {
               postLoaderEvent()
                val response  = appRepo.getRestaurantList()
                response?.let { restaurantList->
                    if (restaurantList.isNotEmpty()) {
                        _mRestaurantListData.postValue(
                            ResultStatus(
                                Status.SUCCESS,
                                restaurantList
                            )
                        )
                    } else {
                        postEmptyEvent()
                    }
                }
            } catch (e: Exception) {
                when (e) {
                    is NetworkErrorException, is UnknownHostException -> {
                        postErrorEvent(message = "Network Error")
                    }
                    else -> {
                        postErrorEvent(message = e.localizedMessage)
                    }
                }
            }
        }
    }

    private fun postLoaderEvent(){
        _mRestaurantListData?.postValue(ResultStatus(Status.LOADING))
    }

    private fun postEmptyEvent(){
        _mRestaurantListData.postValue(ResultStatus(Status.EMPTY))
    }

    private fun postErrorEvent(message:String){
        _mRestaurantListData.postValue(ResultStatus.error(message = message ))
    }

    fun searchRestaurant(searchQuery: String) {
        if (searchQuery.trim().isEmpty()) {
            getRestaurantList()
        } else {
            ioScope.launch {
                try {
                    postLoaderEvent()
                    val response  = appRepo.searchRestaurants(searchQuery)
                    response?.let { restaurantList->
                        if (restaurantList.isNotEmpty()) {
                            _mRestaurantListData.postValue(
                                ResultStatus(
                                    Status.SUCCESS,
                                    restaurantList
                                )
                            )
                        } else {
                           postEmptyEvent()
                        }
                    }
                } catch (e: Exception) {
                    when (e) {
                        is NetworkErrorException, is UnknownHostException -> {
                           postErrorEvent(message = "Network Error")
                        }
                        else -> {
                            postErrorEvent(message = e.localizedMessage)
                        }
                    }
                }
            }
        }
    }
}