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
import com.example.demoone.BR
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding, VM : ViewModel> : DaggerFragment() {
  protected lateinit var binding: B
  lateinit var viewModel: VM

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
    viewModel =// activity?.let {
      ViewModelProvider(activity!!, viewModelFactory)[getViewModelClass()]
    //} ?: throw Exception("Invalid Activity")
    binding.setVariable(BR.viewModel, viewModel)
    return binding.root
  }

  abstract fun getViewModelClass(): Class<VM>

  @LayoutRes
  abstract fun getLayout(): Int
}