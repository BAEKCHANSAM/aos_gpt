package com.android.base.views.container.mainAct

import android.os.Bundle
import androidx.activity.viewModels
import com.android.base.views.base.App
import com.android.base.views.base.BaseActivity
import com.example.custom_base_project.R
import com.example.custom_base_project.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val navRes: Int = R.id.nav_host
    override val layoutRes: Int get() = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels { (application as App).viewModelFactory }


    override fun initViews(savedInstanceState: Bundle?) {
        ui.viewDataBinding.viewModel = viewModel
    }

    override fun addObservers() {}


}