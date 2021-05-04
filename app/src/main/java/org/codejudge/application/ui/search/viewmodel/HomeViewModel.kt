package org.codejudge.application.ui.search.viewmodel

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.codejudge.application.data.remote.AppRepo
import org.codejudge.application.domain.model.RestaurantListResModel
import org.codejudge.application.ui.base.viewmodel.BaseViewModel
import org.codejudge.application.utils.ResultStatus
import org.codejudge.application.utils.Status
import java.net.UnknownHostException
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val appRepo: AppRepo
) : BaseViewModel() {


    private var _mRestaurantListData = MutableLiveData<ResultStatus<List<RestaurantListResModel>>>()
    val mRestaurantList: LiveData<ResultStatus<List<RestaurantListResModel>>> = _mRestaurantListData

    fun getRestaurantList() {
        ioScope.launch {
            try {
                withContext(ioScope.coroutineContext) {
                    _mRestaurantListData?.postValue(ResultStatus(Status.LOADING))
                    appRepo.getRestaurantList()
                }.let { restaurantList ->
                    if (restaurantList.isNotEmpty()) {
                        _mRestaurantListData.postValue(
                            ResultStatus(
                                Status.SUCCESS,
                                restaurantList
                            )
                        )
                    } else {
                        _mRestaurantListData.postValue(ResultStatus(Status.EMPTY, restaurantList))
                    }
                    return@let
                }
            } catch (e: Exception) {
                when (e) {
                    is NetworkErrorException, is UnknownHostException -> {
                        mNoInternetLiveData.postValue(true)
                    }
                    else -> {
                        mErrorData.postValue(e.message)
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
                    withContext(ioScope.coroutineContext) {
                        _mRestaurantListData?.postValue(ResultStatus(Status.LOADING))
                        appRepo.searchRestaurants(searchQuery)
                    }.let { restaurantList ->
                        if (restaurantList.isNotEmpty()) {
                            _mRestaurantListData.postValue(
                                ResultStatus(
                                    Status.SUCCESS,
                                    restaurantList
                                )
                            )
                        } else {
                            _mRestaurantListData.postValue(
                                ResultStatus(
                                    Status.EMPTY,
                                    restaurantList
                                )
                            )
                        }
                        return@let
                    }
                } catch (e: Exception) {
                    when (e) {
                        is NetworkErrorException, is UnknownHostException -> {
                            mNoInternetLiveData.postValue(true)
                        }
                        else -> {
                            mErrorData.postValue(e.message)
                        }
                    }
                }
            }
        }
    }
}