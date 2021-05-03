package org.codejudge.application.ui.base.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import org.codejudge.application.ui.base.viewmodel.BaseViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import org.codejudge.application.R
import org.codejudge.application.utils.snack
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseViewModelActivity<VM : BaseViewModel,VB:ViewBinding>(viewModelClass: KClass<VM>) : DaggerAppCompatActivity() {

    private var mSnackBar:Snackbar?=null

    // Broadcast Receiver to check for the internet connectivity.
    private val mNetworkState = InternetConnectionStateReciever()
    private val mNetworkFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")

    // Boolean to inform whether the internet connectivity is available at the moment.
    private var isNetworkAvailable: Boolean = true


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val viewModel: VM by lazy {
        ViewModelProvider(viewModelStore, viewModelFactory)[viewModelClass.java]
    }

    protected var mbinding:VB?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = getViewBinding()
        setContentView(mbinding?.root)
        setupViews()
        initListeners()
        observeViewModel()
    }

    abstract fun getViewBinding(): VB

    fun getBinding() = mbinding

    /**
     * Display "No network connection" message/UI based on the current state
     * of the network connectivity.
     */
    open fun showInternetError(show:Boolean){}
    /**
     * This method should be used to setup views. e.g Setting RecyclerView Adapters or
     * showing initial state of Activities
     */
    open fun setupViews() {}

    /**
     * This method should be used to initialise the listeners for a view or multiple
     * views in the activity
     */
    open fun initListeners() {}

    /**
     * The viewmodel's LiveData should be observed by overriding this method in the
     * inheriting classes
     */
    open fun observeViewModel() {}


    /**
     * Function to notify the network connectivity state.
     */
    open fun getNetworkAvailability(isConnected: Boolean) {}


    override fun onResume() {
        super.onResume()
        registerReceiver(mNetworkState, mNetworkFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mNetworkState)
    }

    /**
     * Returns the current state of the network connectivity.
     */
    fun getInternetConnectionState() = isNetworkAvailable

    /**
     * BroadcastReceiver Class to listen to the network state changes.
     */
    inner class InternetConnectionStateReciever : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            context?.run {
                val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
                val isConnected: Boolean = activeNetwork?.isConnected == true

                getNetworkAvailability(isConnected)
                isNetworkAvailable = isConnected

                if (!isConnected) {
                    showInternetError(true)
                    mSnackBar = getBinding()?.root?.snack(getString(R.string.no_interet_connection),Snackbar.LENGTH_INDEFINITE)
                } else {
                    showInternetError(false)
                    mSnackBar?.dismiss()
                }
                cm
            }
        }
    }
}

