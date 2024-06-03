package com.android.base.views.common

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.databinding.library.baseAdapters.BR

class UiManager<V : ViewDataBinding, VM : ViewModel>(
  val viewDataBinding: V,
  val viewModel: VM
) {

  init {
    viewDataBinding.setVariable(BR.viewModel, viewModel)
//    viewDataBinding.setVariable(BR.glide, Glide.with(viewDataBinding.root))
    viewDataBinding.executePendingBindings()
  }

}
