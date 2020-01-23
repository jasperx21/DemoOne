package com.example.demoone.ui.home.dashboard

import com.example.demoone.R
import com.example.demoone.databinding.FragmentDashboardBinding
import com.example.demoone.ui.base.BaseFragment

class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>() {
  override fun getViewModelClass(): Class<DashboardViewModel> = DashboardViewModel::class.java

  override fun getLayout(): Int = R.layout.fragment_dashboard
}