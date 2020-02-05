package com.example.demoone

import androidx.databinding.library.BuildConfig
import androidx.multidex.MultiDex
import com.example.demoone.injection.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class BaseApplication : DaggerApplication() {
  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerAppComponent.builder()
        .create(this)
  }

  override fun onCreate() {
    super.onCreate()
    MultiDex.install(this)
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }

}