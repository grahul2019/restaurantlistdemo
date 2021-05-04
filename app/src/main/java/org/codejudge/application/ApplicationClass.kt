package org.codejudge.application

import org.codejudge.application.di.component.AppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import org.codejudge.application.di.component.DaggerAppComponent

class ApplicationClass :DaggerApplication(){
    private var appComponent: AppComponent? = null

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        return appComponent as AppComponent
    }

}