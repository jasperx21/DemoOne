package com.example.demoone.ui.home.splash

import android.os.Bundle
import android.os.Handler
import androidx.navigation.fragment.findNavController
import com.example.demoone.R
import com.example.demoone.databinding.FragmentSplashBinding
import com.example.demoone.ui.base.BaseFragment
import com.example.demoone.ui.home.HomeViewModel

class SplashFragment : BaseFragment<FragmentSplashBinding, HomeViewModel>() {
  override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

  override fun getLayout(): Int = R.layout.fragment_splash

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    Handler().postDelayed({
      if (viewModel.isUserRegistered())
        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
      else
        findNavController().navigate(R.id.action_splashFragment_to_registrationFragment)
    }, 2000)
  }
}