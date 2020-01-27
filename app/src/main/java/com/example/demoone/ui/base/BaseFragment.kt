package com.example.demoone.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.demoone.BR
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding, VM : ViewModel, AVM : ViewModel> :
    DaggerFragment() {
  protected lateinit var binding: B
  lateinit var viewModel: VM
  lateinit var activityViewModel: ViewModel

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
    bindContentView()
    return binding.root
  }

  private fun bindContentView() {
    viewModel = ViewModelProvider(this, viewModelFactory)[getViewModelClass()]
    activityViewModel =
      ViewModelProvider(getActivityViewModelOwner(), viewModelFactory)[getActivityViewModelClass()]
    binding.setVariable(BR.viewModel, viewModel)
  }

  abstract fun getViewModelClass(): Class<VM>

  abstract fun getActivityViewModelClass(): Class<AVM>

  abstract fun getActivityViewModelOwner(): ViewModelStoreOwner

  @LayoutRes
  abstract fun getLayout(): Int
}