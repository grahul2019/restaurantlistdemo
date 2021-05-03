package org.codejudge.application.di.module.scope

import org.codejudge.application.di.module.AppNetworkModule
import org.codejudge.application.di.module.AppUIModule
import dagger.Module

@Module (includes = [AppUIModule::class, AppNetworkModule::class])
abstract class AppModule{

}