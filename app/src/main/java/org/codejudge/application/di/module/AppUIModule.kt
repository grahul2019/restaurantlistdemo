package org.codejudge.application.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.codejudge.application.di.module.scope.ViewModelKey
import org.codejudge.application.ui.search.activities.HomeActivity
import org.codejudge.application.ui.search.viewmodel.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract  class AppUIModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @ContributesAndroidInjector
    abstract fun provideHomeActivity(): HomeActivity

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun provideAppIntroViewModel(viewModel: HomeViewModel): ViewModel
}