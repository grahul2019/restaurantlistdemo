package org.codejudge.application.di.component

import android.app.Application
import org.codejudge.application.ApplicationClass
import org.codejudge.application.di.module.scope.AppModule
import org.codejudge.application.di.module.scope.AppScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class
    ]
)
@AppScope
@Singleton
interface AppComponent:AndroidInjector<ApplicationClass> {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}