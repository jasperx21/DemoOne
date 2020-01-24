package com.example.demoone.ui.home.dashboard

import com.example.demoone.injection.scope.FragmentScope
import dagger.Binds
import dagger.Module
import dagger.android.support.DaggerFragment

@Module
abstract class DashboardFragmentModule {

  @Binds
  @FragmentScope
  abstract fun provideFragment(homeActivity: DashboardFragment): DaggerFragment
}