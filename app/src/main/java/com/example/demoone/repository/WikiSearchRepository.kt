package com.example.demoone.repository

import com.example.demoone.data.model.WikiSearch
import com.example.demoone.data.source.WikiApiService
import io.reactivex.Single

class WikiSearchRepository(private val wikiApiService: WikiApiService) {

  fun getWikiSearchResult(searchString: String): Single<WikiSearch.Result> {
    return wikiApiService.getSearchList("query", "json", "search", searchString)
  }
}