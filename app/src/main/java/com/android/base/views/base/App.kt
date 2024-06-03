package com.android.base.views.base

import androidx.lifecycle.ViewModelProvider
import com.android.base.views.factory.ViewModelFactory

class App : BaseApplication() {
    private val _viewModelFactory: ViewModelFactory by lazy { ViewModelFactory() }
    override val viewModelFactory: ViewModelProvider.Factory
        get() = _viewModelFactory
}