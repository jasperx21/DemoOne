package com.example.demoone.ui.home.login

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.demoone.R
import com.example.demoone.databinding.FragmentLoginBinding
import com.example.demoone.repository.Result.Failure
import com.example.demoone.repository.Result.Success
import com.example.demoone.ui.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
  override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

  override fun getLayout(): Int = R.layout.fragment_login

  override fun onStart() {
    super.onStart()

    viewModel.getLoginState()
        .observe(this, Observer {
          if (it is Success)
            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
          else if (it is Failure)
            Toast.makeText(activity, it.errorResponse!!.message, Toast.LENGTH_LONG).show()
        })

    viewModel.getDeleteUserState()
        .observe(this, Observer {
          if (it is Success)
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        })
  }

}