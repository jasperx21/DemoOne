package com.example.demoone.injection.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demoone.injection.scope.ViewModelScope
import com.example.demoone.ui.home.HomeViewModel
import com.example.demoone.ui.home.dashboard.DashboardViewModel
import com.example.demoone.ui.home.joke.JokeViewModel
import com.example.demoone.ui.home.login.LoginViewModel
import com.example.demoone.ui.home.register.RegistrationViewModel
import com.example.demoone.ui.home.search.SearchViewModel
import com.example.demoone.ui.home.splash.SplashViewModel
import com.example.demoone.utils.ViewModelFactory
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
  @ViewModelScope(JokeViewModel::class)
  abstract fun bindJokeViewModel(jokeViewModel: JokeViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelScope(SearchViewModel::class)
  abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

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
  @IntoMap
  @ViewModelScope(SplashViewModel::class)
  abstract fun bindSplashFragmentViewModel(splashViewModel: SplashViewModel): ViewModel

  @Binds
  internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
