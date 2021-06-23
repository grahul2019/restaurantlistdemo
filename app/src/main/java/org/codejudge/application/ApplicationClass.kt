package org.codejudge.application

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