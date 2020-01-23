package com.example.demoone.ui.home.dashboard

import com.example.demoone.R
import com.example.demoone.databinding.FragmentDashboardBinding
import com.example.demoone.ui.base.BaseFragment
import com.example.demoone.ui.home.HomeViewModel

class DashboardFragment : BaseFragment<FragmentDashboardBinding, HomeViewModel>() {
  override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

  override fun getLayout(): Int = R.layout.fragment_registration
}