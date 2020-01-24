package com.example.demoone.injection.module

import com.example.demoone.AppConstants
import com.example.demoone.data.source.WikiApiService
import com.example.demoone.repository.WikiSearchRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

  @Provides @Singleton internal fun provideOkHttpClient(): OkHttpClient {
    val httpBuilder = OkHttpClient.Builder()
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    httpBuilder.interceptors()
        .add(httpLoggingInterceptor)
    return httpBuilder.build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(AppConstants.WIKI_API_BASE_URL)
//        .client(okHttpClient)
        .build()

  @Provides
  @Singleton
  fun provideWikiApiService(retrofit: Retrofit): WikiApiService =
    retrofit.create(WikiApiService::class.java)

  @Provides
  @Singleton
  fun provideWikiSearchRepository(wikiApiService: WikiApiService): WikiSearchRepository =
    WikiSearchRepository(wikiApiService)

}
