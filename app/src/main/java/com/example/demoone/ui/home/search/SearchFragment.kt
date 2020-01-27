package com.example.demoone.ui.home.search

import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoone.R
import com.example.demoone.databinding.FragmentSearchBinding
import com.example.demoone.ui.base.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {
  override fun getViewModelClass(): Class<SearchViewModel> = SearchViewModel::class.java

  override fun getLayout(): Int = R.layout.fragment_search

  override fun onStart() {
    super.onStart()

    val adapter = WikiSearchResultRecyclerAdapter()
    binding.recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    binding.recyclerView.adapter = adapter

    viewModel.init()
    viewModel.getSearchList()
        .observe(this, Observer {
          adapter.setSearchResults(it)
        })

    binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
          viewModel.getSearchResults(it)
        }
        return true

      }

      override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
          viewModel.getSearchResults(it)
        }
        return false
      }

    })
  }

}