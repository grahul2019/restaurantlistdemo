package org.codejudge.application.ui.search.activities

import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.codejudge.application.R
import org.codejudge.application.databinding.ActivityMainBinding
import org.codejudge.application.domain.model.RestaurantListResModel
import org.codejudge.application.ui.base.activities.BaseViewModelActivity
import org.codejudge.application.ui.search.adapter.RestaurantListAdapter
import org.codejudge.application.ui.search.viewmodel.HomeViewModel
import org.codejudge.application.utils.*


class HomeActivity : BaseViewModelActivity<HomeViewModel, ActivityMainBinding>(HomeViewModel::class){

    private var textChangedJob: Job? = null

    private val rvAdapter by lazy {
        RestaurantListAdapter()
    }

    override fun getViewBinding(): ActivityMainBinding  = ActivityMainBinding.inflate(layoutInflater)

    override fun ActivityMainBinding.setupViews() {
        rvRestaurants?.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity,RecyclerView.VERTICAL,false)
            adapter = rvAdapter
        }
        fetchData()
    }

    private fun fetchData() {
        if (this@HomeActivity.isNetworkAvailable(this@HomeActivity)){
            viewModel.getRestaurantList()
        }else{
            getBinding()?.root?.rootView?.snack(getString(R.string.no_interet_connection))?.setAction("Retry") {
                fetchData()
            }?.show()
        }
    }

    @FlowPreview
    override fun ActivityMainBinding.initListeners() {
        fun toggleClearView(show:Boolean){
            if (show){
                ivClear?.show()
            }else{
                ivClear?.remove()
            }
        }

       editSearch?.apply {
            var searchFor = ""
            filters = arrayOf(EmojiExcludeFilter())
            doOnTextChanged { charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->
                val searchText = charSequence.toString().trim()
                if (searchText != searchFor) {
                    searchFor = searchText
                    textChangedJob?.cancel()
                    textChangedJob = CoroutineScope(Dispatchers.Main).launch {
                        delay(2000)
                        if (searchText == searchFor) {
                            searchRestaurants(searchText)
                            hideKeyboard()
                        }
                    }
                }
                toggleClearView(charSequence.toString()?.isNotEmpty())
                if (charSequence?.isEmpty() == true){
                    hideKeyboard()
                }
            }
        }
        ivClear?.setOnClickListener {
            editSearch?.setText("")
            toggleClearView(false)
            hideKeyboard()
        }

    }

    private fun searchRestaurants(query:String){
        if (this@HomeActivity.isNetworkAvailable(this@HomeActivity)){
            viewModel.searchRestaurant(query)
        }else{
            getBinding()?.root?.rootView?.snack(getString(R.string.no_interet_connection))?.setAction("Retry") {
                viewModel.searchRestaurant(query)
            }?.show()
        }
    }

    override fun ActivityMainBinding.observeViewModel() {

        fun toggleListView(show:Boolean){
            if (show){
                rvRestaurants?.show()
            }else{
                rvRestaurants?.remove()
            }
        }

        fun toggleLoaderView(show: Boolean){
            if (show){
                loader?.root?.show()
            }else{
                loader?.root?.remove()
            }
        }

        fun toggleEmptyView(show:Boolean){
            if (show){
                emptyView?.root?.show()
            }else{
                emptyView?.root?.remove()
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
                        it.message?.let { errorMsg ->
                            root?.rootView?.snack(errorMsg)?.show()
                        }
                    }
                    Status.LOADING->{
                        toggleListView(false)
                        toggleLoaderView(true)
                        toggleEmptyView(false)
                    }
                }
            }
        }
    }
}
