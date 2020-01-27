package com.example.demoone.ui.home.register

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import com.example.demoone.R
import com.example.demoone.data.source.Result.Failure
import com.example.demoone.data.source.Result.Success
import com.example.demoone.databinding.FragmentRegistrationBinding
import com.example.demoone.ui.base.BaseFragment
import com.example.demoone.ui.home.HomeActivity
import com.example.demoone.ui.home.HomeViewModel

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding, RegistrationViewModel, HomeViewModel>() {

  override fun getViewModelClass(): Class<RegistrationViewModel> = RegistrationViewModel::class.java
  override fun getActivityViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java
  override fun getActivityViewModelOwner(): ViewModelStoreOwner = (activity as HomeActivity)
  override fun getLayout(): Int = R.layout.fragment_registration

  override fun onStart() {
    super.onStart()
    viewModel.getRegistrationState()
        .observe(this, Observer {
          if (it is Success)
            findNavController().navigate(
                R.id.action_toDashboard_clear_stack
            )
          else if (it is Failure)
            Toast.makeText(activity, it.errorResponse!!.message, Toast.LENGTH_LONG).show()
        })
  }
}