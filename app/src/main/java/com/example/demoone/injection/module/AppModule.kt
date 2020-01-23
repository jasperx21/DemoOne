package com.example.demoone.injection.module

import android.content.SharedPreferences
import com.example.demoone.repository.UserManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

  @Singleton
  @Provides
  fun provideUserManager(sharedPreferences: SharedPreferences): UserManager {
    return UserManager(sharedPreferences)
  }
}