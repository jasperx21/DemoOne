package com.example.demoone.ui.home.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.demoone.BR
import com.example.demoone.R
import com.example.demoone.data.model.WikiSearch
import com.example.demoone.databinding.IndiviewWikiSearchResultBinding
import com.example.demoone.ui.home.search.WikiSearchResultRecyclerAdapter.WikiSearchResultViewHolder

class WikiSearchResultRecyclerAdapter : RecyclerView.Adapter<WikiSearchResultViewHolder>() {

  private var searchResultList: ArrayList<WikiSearch.Search> = ArrayList()

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): WikiSearchResultViewHolder {
    val binding: IndiviewWikiSearchResultBinding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.context), R.layout.indiview_wiki_search_result, parent, false
    )
    return WikiSearchResultViewHolder(binding)
  }

  override fun getItemCount(): Int {
    return searchResultList.size
  }

  fun setSearchResults(searchResultList: ArrayList<WikiSearch.Search>) {
    this.searchResultList = searchResultList
    notifyDataSetChanged()
  }

  override fun onBindViewHolder(
    holder: WikiSearchResultViewHolder,
    position: Int
  ) {
    holder.binding.setVariable(BR.searchResult, searchResultList[position])
  }

  class WikiSearchResultViewHolder(val binding: IndiviewWikiSearchResultBinding) :
      RecyclerView.ViewHolder(binding.root)

}