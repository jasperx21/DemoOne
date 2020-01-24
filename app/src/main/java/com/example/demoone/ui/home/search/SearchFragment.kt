package com.example.demoone.ui.home.search

import com.example.demoone.R
import com.example.demoone.databinding.FragmentSearchBinding
import com.example.demoone.ui.base.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {
    override fun getViewModelClass(): Class<SearchViewModel> = SearchViewModel::class.java

    override fun getLayout(): Int = R.layout.fragment_search

    override fun onStart() {
        super.onStart()

    }

}