package com.example.demoone.ui.home.register

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.demoone.R
import com.example.demoone.databinding.FragmentRegistrationBinding
import com.example.demoone.repository.Result.Failure
import com.example.demoone.repository.Result.Success
import com.example.demoone.ui.base.BaseFragment

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding, RegistrationViewModel>() {
  override fun getViewModelClass(): Class<RegistrationViewModel> =
    RegistrationViewModel::class.java

  override fun getLayout(): Int = R.layout.fragment_registration

  override fun onStart() {
    super.onStart()
    viewModel.getRegistrationState()
        .observe(this, Observer {
          if (it is Success)
            findNavController().navigate(R.id.action_registrationFragment_to_dashboardFragment)
          else if (it is Failure)
            Toast.makeText(activity, it.errorResponse!!.message, Toast.LENGTH_LONG).show()
        })
  }
}