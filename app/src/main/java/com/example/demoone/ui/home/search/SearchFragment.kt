package com.example.demoone.ui.home.search

import android.util.Log
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoone.R
import com.example.demoone.databinding.FragmentSearchBinding
import com.example.demoone.ui.base.BaseFragment
import com.example.demoone.ui.home.HomeActivity
import com.example.demoone.ui.home.HomeViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit.MILLISECONDS

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel, HomeViewModel>() {

  override fun getViewModelClass(): Class<SearchViewModel> = SearchViewModel::class.java
  override fun getActivityViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java
  override fun getActivityViewModelOwner(): ViewModelStoreOwner = (activity as HomeActivity)
  override fun getLayout(): Int = R.layout.fragment_search

  private lateinit var disposable: Disposable

  override fun onStart() {
    super.onStart()
    initializeRecyclerView()
    addTextChangeListener()
    addOnScrollListenerToRecyclerView()
    viewModel.loadArguments(arguments)
  }

  private fun initializeRecyclerView() {
    val adapter = WikiSearchResultRecyclerAdapter()
    binding.recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    binding.recyclerView.adapter = adapter

    viewModel.getSearchList()
        .observe(this, Observer {
          adapter.setSearchResults(it)
        })

  }

  private fun addTextChangeListener() {
    disposable = createTextChangeObservable(binding.searchView)
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { viewModel.getSearchResults(it) },
            { Log.d("myTag", it.toString()) }
        )
  }

  private fun addOnScrollListenerToRecyclerView() {
    binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int
      ) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy < 0) {
          binding.rootLayout.transitionToStart()
        } else if (dy > 0) {
          binding.rootLayout.transitionToEnd()
        }
      }
    })
  }

  private fun createTextChangeObservable(searchView: SearchView): Observable<String> {
    return Observable.create<String> { emitter ->
      val textChangeListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
          query?.let {
            emitter.onNext(it)
          }
          return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
          newText?.let {
            emitter.onNext(it)
          }
          return false
        }
      }
      searchView.setOnQueryTextListener(textChangeListener)

      emitter.setCancellable {
        searchView.setOnQueryTextListener(null)
      }
    }
        .debounce(500, MILLISECONDS)
  }

  override fun onStop() {
    super.onStop()
    if (!disposable.isDisposed) {
      disposable.dispose()
    }
  }

}