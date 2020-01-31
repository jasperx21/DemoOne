package com.example.demoone.ui.home.dashboard

import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import com.example.demoone.R
import com.example.demoone.databinding.FragmentDashboardBinding
import com.example.demoone.ui.base.BaseFragment
import com.example.demoone.ui.home.HomeActivity
import com.example.demoone.ui.home.HomeViewModel

class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel, HomeViewModel>() {

  override fun getViewModelClass(): Class<DashboardViewModel> = DashboardViewModel::class.java
  override fun getActivityViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java
  override fun getActivityViewModelOwner(): ViewModelStoreOwner = (activity as HomeActivity)
  override fun getLayout(): Int = R.layout.fragment_dashboard

  override fun onStart() {
    super.onStart()
    binding.btnJoke.setOnClickListener {
      findNavController().navigate(R.id.action_dashboardFragment_to_jokeFragment)
    }

    binding.btnSearch.setOnClickListener {
      findNavController().navigate(R.id.action_dashboardFragment_to_searchFragment)
    }

    binding.btnMusic.setOnClickListener {
      findNavController().navigate(R.id.action_dashboardFragment_to_musicFragment)
    }
  }

}