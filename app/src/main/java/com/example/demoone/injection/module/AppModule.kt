package com.example.demoone.injection.module

import android.content.SharedPreferences
import com.example.demoone.data.source.UserManager
import com.example.demoone.repository.MainRepository
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

  @Singleton
  @Provides
  fun provideMainRepository(userManager: UserManager): MainRepository {
    return MainRepository(userManager)
  }
}