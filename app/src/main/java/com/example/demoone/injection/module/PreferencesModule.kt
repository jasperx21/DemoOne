package com.example.demoone.injection.module

import android.content.Context
import android.content.SharedPreferences
import com.example.demoone.AppConstants
import com.example.demoone.injection.qualifiers.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferencesModule {
  @Provides
  @Singleton
  fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
    return context.getSharedPreferences(
        AppConstants.SHARED_PREFERENCE_NAME,
        Context.MODE_PRIVATE
    )
  }
}
