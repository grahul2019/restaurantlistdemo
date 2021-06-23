package org.codejudge.application.ui.base.activities

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import org.codejudge.application.ui.base.viewmodel.BaseViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseViewModelActivity<VM : BaseViewModel,VB:ViewBinding>(viewModelClass: KClass<VM>) : DaggerAppCompatActivity() {

    var mSnackBar: Snackbar? = null

    // Boolean to inform whether the internet connectivity is available at the moment.
    var isNetworkAvailable = MutableLiveData<Boolean>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val viewModel: VM by lazy {
        ViewModelProvider(viewModelStore, viewModelFactory)[viewModelClass.java]
    }

    private var mbinding: VB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = getViewBinding()
        setContentView(mbinding?.root)
        getBinding()?.setupViews()
        getBinding()?.initListeners()
        getBinding()?.observeViewModel()
    }

    abstract fun getViewBinding(): VB

    fun getBinding() = mbinding

    //This method should be used to setup views. e.g Setting RecyclerView Adapters or showing initial state of Activities
    open fun VB.setupViews() {}

    //This method should be used to initialise the listeners for a view or multiple views in the activity
    open fun VB.initListeners() {}

    //The viewmodel's LiveData should be observed by overriding this method in the inheriting classes
    open fun VB.observeViewModel() {}
}

