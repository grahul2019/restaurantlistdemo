package org.codejudge.application.ui.search.activities

import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.codejudge.application.R
import org.codejudge.application.databinding.ActivityMainBinding
import org.codejudge.application.domain.model.RestaurantListResModel
import org.codejudge.application.ui.base.activities.BaseViewModelActivity
import org.codejudge.application.ui.search.adapter.RestaurantListAdapter
import org.codejudge.application.ui.search.viewmodel.HomeViewModel
import org.codejudge.application.utils.*

class HomeActivity : BaseViewModelActivity<HomeViewModel, ActivityMainBinding>(HomeViewModel::class){

    private val rvAdapter by lazy {
        RestaurantListAdapter()
    }

    override fun getViewBinding(): ActivityMainBinding  = ActivityMainBinding.inflate(layoutInflater)

    override fun setupViews() {
        super.setupViews()
        getBinding()?.rvRestaurants?.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity,RecyclerView.VERTICAL,false)
            adapter = rvAdapter
        }
        if (getInternetConnectionState()){
            viewModel.getRestaurantList()
        }else{
            showInternetError(getInternetConnectionState())
        }
    }

    override fun initListeners() {
        super.initListeners()
        fun toggleClearView(show:Boolean){
            if (show){
                getBinding()?.ivClear?.show()
            }else{
                getBinding()?.ivClear?.remove()
            }
        }
        getBinding()?.editSearch?.apply {
            filters = arrayOf(EmojiExcludeFilter())
            doOnTextChanged { charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->
                if (getInternetConnectionState()){
                    viewModel.searchRestaurant(charSequence.toString())
                }else{
                    showInternetError(getInternetConnectionState())
                }
                toggleClearView(charSequence.toString()?.isNotEmpty())
                hideKeyboard()
            }
        }
        getBinding()?.ivClear?.setOnClickListener {
            getBinding()?.editSearch?.setText("")
            toggleClearView(false)
            hideKeyboard()
        }

    }

    override fun observeViewModel() {

        fun toggleListView(show:Boolean){
            if (show){
                getBinding()?.rvRestaurants?.show()
            }else{
                getBinding()?.rvRestaurants?.remove()
            }
        }

        fun toggleLoaderView(show: Boolean){
            if (show){
                getBinding()?.loader?.root?.show()
            }else{
                getBinding()?.loader?.root?.remove()
            }
        }

        fun toggleEmptyView(show:Boolean){
            if (show){
                getBinding()?.emptyView?.root?.show()
            }else{
                getBinding()?.emptyView?.root?.remove()
            }
        }

        fun setData(data: List<RestaurantListResModel>?) {
            data?.let{ list-> rvAdapter.setListData(list)}
        }


        viewModel.apply {
            mRestaurantList.observe(this@HomeActivity){
                when(it.status){
                    Status.EMPTY ->{
                        toggleListView(false)
                        toggleLoaderView(false)
                        toggleEmptyView(true)
                    }
                    Status.SUCCESS->{
                        toggleListView(true)
                        toggleLoaderView(false)
                        setData(it.data)
                        toggleEmptyView(false)
                    }
                    Status.ERROR->{
                        toggleLoaderView(false)
                        toggleListView(false)
                        toggleEmptyView(false)
                    }
                    Status.LOADING->{
                        toggleListView(false)
                        toggleLoaderView(true)
                        toggleEmptyView(false)
                    }
                }
            }

            mNoInternetLiveData.observe(this@HomeActivity){isNotConnected->
                toggleLoaderView(false)
                showInternetError(!isNotConnected)

            }
            mErrorData.observe(this@HomeActivity){
                toast(it)
            }
        }
    }
}
