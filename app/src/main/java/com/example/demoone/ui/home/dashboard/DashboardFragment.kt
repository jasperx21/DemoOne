package com.example.demoone.ui.home.dashboard

import androidx.navigation.fragment.findNavController
import com.example.demoone.R
import com.example.demoone.databinding.FragmentDashboardBinding
import com.example.demoone.ui.base.BaseFragment

class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>() {
  override fun getViewModelClass(): Class<DashboardViewModel> = DashboardViewModel::class.java

  override fun getLayout(): Int = R.layout.fragment_dashboard

  override fun onStart() {
    super.onStart()
    binding.btnJoke.setOnClickListener {
      findNavController().navigate(R.id.action_dashboardFragment_to_jokeFragment)
    }

    binding.btnSearch.setOnClickListener {
      findNavController().navigate(R.id.action_dashboardFragment_to_searchFragment)
    }
  }
}