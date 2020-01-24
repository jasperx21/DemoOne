package com.example.demoone.data.source

import com.example.demoone.data.model.WikiSearch
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WikiApiService {
  @GET("api.php")
  fun getSearchList(
    @Query("action") action: String,
    @Query("format") format: String,
    @Query("list") list: String,
    @Query("srsearch") srsearch: String
  ): Single<WikiSearch.Result>
}