package com.android.base.views.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.base.views.common.Event
import com.android.base.views.common.EventObserver
import com.android.base.views.common.UiManager

abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    protected lateinit var ui: UiManager<V, VM>
    protected lateinit var actViewModel: BaseViewModel
    protected val viewModel: VM get() = ui.viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        
        val viewLifecycleOwner = this.viewLifecycleOwner
        ui = UiManager(
            DataBindingUtil.inflate(inflater, layoutRes, container, false),
            ViewModelProvider(this, BaseApplication.instance!!.viewModelFactory)[viewModelClass]
        )
        actViewModel =
            ViewModelProvider(
                requireActivity(),
                BaseApplication.instance!!.viewModelFactory
            )[actViewModelClass]

        ui.viewDataBinding.lifecycleOwner = viewLifecycleOwner

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!viewModel.isOnLoading) {
                        viewModel.onBackPressed()
                    }
                }
            })

        return ui.viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(savedInstanceState)
        addObservers()
    }

    protected fun <T> LiveData<T>.observe(observer: (value: T) -> Unit) {
        observe(viewLifecycleOwner, Observer(observer))
    }

    protected fun <T> LiveData<Event<T>>.observeEvent(observer: (value: T) -> Unit) {
        observe(viewLifecycleOwner, EventObserver(observer))
    }

    protected fun LiveData<Event<Unit>>.observeEvent(observer: () -> Unit) {
        observe(viewLifecycleOwner, EventObserver { observer.invoke() })
    }

    @get:LayoutRes
    protected abstract val layoutRes: Int

    @get:IdRes
    protected abstract val destinationId: Int
    protected abstract val viewModelClass: Class<VM>
    protected abstract val actViewModelClass: Class<out BaseViewModel>
    protected abstract fun initViews(savedInstanceState: Bundle?)
    protected abstract fun addObservers()


}