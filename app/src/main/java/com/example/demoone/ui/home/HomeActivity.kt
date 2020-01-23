package com.example.demoone.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.demoone.R
import com.example.demoone.databinding.ActivityHomeBinding
import com.example.demoone.ui.base.BaseActivity

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {

  override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

  override fun layoutId(): Int = R.layout.activity_home

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.progress.observe(this, Observer {
      binding.progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
    })
  }

}
