package org.codejudge.application.ui.search.activities

import org.codejudge.application.databinding.ActivityMainBinding
import org.codejudge.application.ui.base.activities.BaseViewModelActivity
import org.codejudge.application.ui.search.viewmodel.HomeViewModel
import org.codejudge.application.utils.toast

class HomeActivity : BaseViewModelActivity<HomeViewModel, ActivityMainBinding>(HomeViewModel::class){

    override fun getViewBinding(): ActivityMainBinding  = ActivityMainBinding.inflate(layoutInflater)

    override fun setupViews() {
        super.setupViews()
        viewModel.getRestaurantList()
    }

    override fun observeViewModel() {
        viewModel.apply {
            mRestaurantList.observe(this@HomeActivity){
                toast(it.size?.toString())
            }
        }
    }
}
