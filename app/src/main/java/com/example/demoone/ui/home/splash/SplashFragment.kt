package com.example.demoone.ui.home.splash

import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.demoone.R
import com.example.demoone.databinding.FragmentSplashBinding
import com.example.demoone.ui.base.BaseFragment

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {
    override fun getViewModelClass(): Class<SplashViewModel> = SplashViewModel::class.java

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
                    arguments,
                    NavOptions.Builder().setPopUpTo(
                        R.id.splashFragment,
                        true
                    ).build()
                )
            })

  }

}