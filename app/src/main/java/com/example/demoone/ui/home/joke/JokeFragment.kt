package com.example.demoone.ui.home.joke

import com.example.demoone.R
import com.example.demoone.databinding.FragmentJokeBinding
import com.example.demoone.ui.base.BaseFragment

class JokeFragment : BaseFragment<FragmentJokeBinding, JokeViewModel>() {
    override fun getViewModelClass(): Class<JokeViewModel> = JokeViewModel::class.java

    override fun getLayout(): Int = R.layout.fragment_joke

}