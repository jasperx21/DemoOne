package com.example.demoone.ui.home.register

import com.example.demoone.R
import com.example.demoone.databinding.FragmentRegistrationBinding
import com.example.demoone.ui.base.BaseFragment
import com.example.demoone.ui.home.HomeViewModel

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding, HomeViewModel>() {
  override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

  override fun getLayout(): Int = R.layout.fragment_registration

  override fun onStart() {
    super.onStart()
    binding.btnRegister.setOnClickListener {
      viewModel.registerUser()
    }
  }
}