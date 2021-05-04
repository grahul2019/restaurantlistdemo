package org.codejudge.application

import org.codejudge.application.di.component.AppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import org.codejudge.application.di.component.DaggerAppComponent

class ApplicationClass :DaggerApplication(){

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .build()
    }

}