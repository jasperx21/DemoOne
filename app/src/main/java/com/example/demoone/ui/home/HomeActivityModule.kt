package com.example.demoone.ui.home

import android.content.Context
import com.example.demoone.injection.module.BaseActivityModule
import com.example.demoone.injection.qualifiers.ActivityContext
import com.example.demoone.injection.scope.ActivityScope
import com.example.demoone.injection.scope.FragmentScope
import com.example.demoone.ui.home.dashboard.DashboardFragment
import com.example.demoone.ui.home.dashboard.DashboardFragmentModule
import com.example.demoone.ui.home.joke.JokeFragment
import com.example.demoone.ui.home.login.LoginFragment
import com.example.demoone.ui.home.register.RegistrationFragment
import com.example.demoone.ui.home.search.SearchFragment
import com.example.demoone.ui.home.splash.SplashFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerAppCompatActivity

@Module(includes = [BaseActivityModule::class])
abstract class HomeActivityModule {

  @FragmentScope
  @ContributesAndroidInjector
  internal abstract fun showSplashFragment(): SplashFragment

  @FragmentScope
  @ContributesAndroidInjector(modules = [DashboardFragmentModule::class])
  internal abstract fun showDashboardFragment(): DashboardFragment

  @FragmentScope
  @ContributesAndroidInjector
  internal abstract fun showSearchFragment(): SearchFragment

  @FragmentScope
  @ContributesAndroidInjector
  internal abstract fun showJokeFragment(): JokeFragment

  @FragmentScope
  @ContributesAndroidInjector
  internal abstract fun showRegistrationFragment(): RegistrationFragment

  @FragmentScope
  @ContributesAndroidInjector
  internal abstract fun showLoginFragment(): LoginFragment

  @Binds
  @ActivityContext
  abstract fun provideActivityContext(activity: HomeActivity): Context

  @Binds
  @ActivityScope
  abstract fun provideActivity(homeActivity: HomeActivity): DaggerAppCompatActivity
}