package com.example.demoone.injection.module

import android.content.SharedPreferences
import com.example.demoone.data.source.UserManager
import com.example.demoone.repository.MainRepository
import com.example.demoone.repository.WikiSearchRepository
import com.example.demoone.utils.IRxSchedulers
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
  fun provideMainRepository(
    userManager: UserManager,
    wikiSearchRepository: WikiSearchRepository
  ): MainRepository {
    return MainRepository(userManager, wikiSearchRepository)
  }

  @Provides
  @Singleton internal fun provideRxSchedulers(): IRxSchedulers {
    return object : IRxSchedulers {
      override fun main() = AndroidSchedulers.mainThread()
      override fun io() = Schedulers.io()
    }
  }
}