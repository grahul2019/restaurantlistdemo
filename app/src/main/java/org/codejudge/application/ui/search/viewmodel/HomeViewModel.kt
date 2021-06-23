package org.codejudge.application.ui.search.viewmodel

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
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
                _mRestaurantListData?.postValue(ResultStatus(Status.LOADING))
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
                        _mRestaurantListData.postValue(ResultStatus(Status.EMPTY))
                    }
                }
            } catch (e: Exception) {
                when (e) {
                    is NetworkErrorException, is UnknownHostException -> {
                        _mRestaurantListData.postValue(ResultStatus.error(message = "Network Error"))
                    }
                    else -> {
                        _mRestaurantListData.postValue(ResultStatus.error(message = e.localizedMessage))
                    }
                }
            }
        }
    }

    fun searchRestaurant(searchQuery: String) {
        if (searchQuery.trim().isEmpty()) {
            getRestaurantList()
        } else {
            ioScope.launch {
                try {
                    _mRestaurantListData?.postValue(ResultStatus(Status.LOADING))
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
                            _mRestaurantListData.postValue(ResultStatus(Status.EMPTY))
                        }
                    }
                } catch (e: Exception) {
                    when (e) {
                        is NetworkErrorException, is UnknownHostException -> {
                            _mRestaurantListData.postValue(ResultStatus.error(message = "Network Error"))
                        }
                        else -> {
                            _mRestaurantListData.postValue(ResultStatus.error(message = e.localizedMessage))
                        }
                    }
                }
            }
        }
    }
}