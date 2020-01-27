package com.example.demoone.ui.home.splash

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import com.example.demoone.R
import com.example.demoone.databinding.FragmentSplashBinding
import com.example.demoone.ui.base.BaseFragment
import com.example.demoone.ui.home.HomeActivity
import com.example.demoone.ui.home.HomeViewModel

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel, HomeViewModel>() {

  override fun getViewModelClass(): Class<SplashViewModel> = SplashViewModel::class.java
  override fun getActivityViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java
  override fun getActivityViewModelOwner(): ViewModelStoreOwner = (activity as HomeActivity)
  override fun getLayout(): Int = R.layout.fragment_splash

  override fun onStart() {
    super.onStart()

    viewModel.isUserRegistered()
    viewModel.getIsUserRegistered()
        .observe(activity!!, Observer {
          val destination =
            if (it) R.id.action_splashFragment_to_loginFragment
            else R.id.action_splashFragment_to_registrationFragment
          val arguments = if (it) viewModel.createArgumentsFromSplashToLogin() else null
          findNavController().navigate(
              destination,
              arguments
          )
        })

  }

}