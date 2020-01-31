package com.example.demoone.ui.home.search

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.demoone.data.model.WikiSearch.Search
import com.example.demoone.repository.MainRepository
import com.example.demoone.ui.base.BaseViewModel
import com.example.demoone.utils.IRxSchedulers
import timber.log.Timber
import javax.inject.Inject

class SearchViewModel @Inject constructor() : BaseViewModel() {

  @Inject
  lateinit var mainRepository: MainRepository
  @Inject
  lateinit var schedulers: IRxSchedulers

    private var searchList = MutableLiveData<ArrayList<Search>>(arrayListOf())

  fun getSearchList(): LiveData<ArrayList<Search>> {
    return searchList
  }
//
//  fun init() {
//    searchList.postValue()
//  }

  fun loadArguments(arguments: Bundle?) {
    arguments?.let {
      if (it.containsKey("query"))
        getSearchResults(it.getString("query", ""))
    }
  }

  fun getSearchResults(queryText: String) {
    mainRepository.progress.value = true
    addDisposable(mainRepository.wikiSearchRepository
        .getWikiSearchResult(queryText)
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.main())
        .doFinally { mainRepository.progress.postValue(false) }
        .subscribe({
          try {
            searchList.postValue(it.query.search)
          } catch (e: Exception) {
            searchList.postValue(arrayListOf())
          }
        }, {
          Timber.d(it)
        })
    )
  }
}