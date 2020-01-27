package com.example.demoone.ui.home.joke

import androidx.lifecycle.ViewModelStoreOwner
import com.example.demoone.R
import com.example.demoone.databinding.FragmentJokeBinding
import com.example.demoone.ui.base.BaseFragment
import com.example.demoone.ui.home.HomeActivity
import com.example.demoone.ui.home.HomeViewModel

class JokeFragment : BaseFragment<FragmentJokeBinding, JokeViewModel, HomeViewModel>() {

  override fun getViewModelClass(): Class<JokeViewModel> = JokeViewModel::class.java
  override fun getActivityViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java
  override fun getActivityViewModelOwner(): ViewModelStoreOwner = (activity as HomeActivity)
  override fun getLayout(): Int = R.layout.fragment_joke

}