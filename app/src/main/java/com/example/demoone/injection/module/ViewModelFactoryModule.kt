package com.mutualmobile.praxis.injection.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demoone.injection.scope.ViewModelScope
import com.example.demoone.ui.home.HomeViewModel
import com.example.demoone.ui.home.dashboard.DashboardViewModel
import com.example.demoone.ui.home.login.LoginViewModel
import com.example.demoone.ui.home.register.RegistrationViewModel
import com.mutualmobile.praxis.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelFactoryModule {

  @Binds
  @IntoMap
  @ViewModelScope(HomeViewModel::class)
  abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelScope(DashboardViewModel::class)
  abstract fun bindDashboardFragmentViewModel(dashboardViewModel: DashboardViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelScope(RegistrationViewModel::class)
  abstract fun bindRegistrationFragmentViewModel(registrationViewModel: RegistrationViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelScope(LoginViewModel::class)
  abstract fun bindLoginFragmentViewModel(loginViewModel: LoginViewModel): ViewModel

  @Binds
  internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
