package com.example.demoone.ui.home.login

import com.example.demoone.R
import com.example.demoone.databinding.FragmentLoginBinding
import com.example.demoone.ui.base.BaseFragment
import com.example.demoone.ui.home.HomeViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding, HomeViewModel>() {
  override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

  override fun getLayout(): Int = R.layout.fragment_login

}