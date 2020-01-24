package com.example.demoone.injection.component

import android.content.Context
import com.example.demoone.BaseApplication
import com.example.demoone.injection.module.ActivityBindingModule
import com.example.demoone.injection.module.AppModule
import com.example.demoone.injection.module.NetworkModule
import com.example.demoone.injection.module.PreferencesModule
import com.example.demoone.injection.module.ViewModelFactoryModule
import com.example.demoone.injection.qualifiers.ApplicationContext
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, PreferencesModule::class, ViewModelFactoryModule::class,
      AndroidSupportInjectionModule::class, ActivityBindingModule::class, NetworkModule::class]
)
interface AppComponent : AndroidInjector<BaseApplication> {
  @Component.Builder
  abstract class Builder : AndroidInjector.Builder<BaseApplication>() {
    @BindsInstance
    abstract fun appContext(@ApplicationContext context: Context)

    override fun seedInstance(instance: BaseApplication?) {
      appContext(instance!!.applicationContext)
    }
  }
}