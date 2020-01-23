package com.mutualmobile.praxis.injection.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demoone.injection.scope.ViewModelScope
import com.example.demoone.ui.home.HomeViewModel
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
//
//  @Binds
//  @IntoMap
//  @ViewModelScope(FragmentTestViewModel::class)
//  abstract fun bindFragmentTestViewModel(fragmentTestViewModel: FragmentTestViewModel): ViewModel
//
//  @Binds
//  @IntoMap
//  @ViewModelScope(HomeViewModel::class)
//  abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel
//
//  @Binds
//  @IntoMap
//  @ViewModelScope(AboutViewModel::class)
//  abstract fun bindAboutViewModel(aboutViewModel: AboutViewModel): ViewModel

  @Binds
  internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
