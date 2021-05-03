package org.codejudge.application.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.codejudge.application.data.remote.AppRepo
import org.codejudge.application.domain.model.RestaurantListResModel
import org.codejudge.application.ui.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val appRepo: AppRepo
): BaseViewModel() {


    private var _mRestaurantListData = MutableLiveData<List<RestaurantListResModel>>()
    val mRestaurantList:LiveData<List<RestaurantListResModel>>  = _mRestaurantListData

    fun getRestaurantList() {
        ioScope.launch {
            withContext(ioScope.coroutineContext){
                appRepo.getRestaurantList()
            }?.let { repos ->
                _mRestaurantListData?.postValue(repos)
                return@let
            } ?: run {

                return@run
            }
        }
    }
}