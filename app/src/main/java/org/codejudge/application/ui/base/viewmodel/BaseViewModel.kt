package org.codejudge.application.ui.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class BaseViewModel : ViewModel(){
    // Coroutine Job to handle operations on any particular coroutine scope. This job is cancelled
    // when the ViewModel is destroyed.
    private val jobDelegate = lazy { SupervisorJob() }

    private val job by jobDelegate

    private val isInitialCallMade by lazy { MutableLiveData<Boolean>() }

    private val isScreenInitialCallMade by lazy { MutableLiveData<Map<String, Boolean>>() }

    // Coroutine Scope that can be used to run any operation on the IO thread.
    protected val ioScope by lazy { CoroutineScope(job + Dispatchers.IO) }

    override fun onCleared() {
        super.onCleared()
        // Cancelling all the children of the SupervisorJob at once if the
        // ViewModel gets cleared.
        job.cancel()
    }
}