package com.android.base.views.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.base.views.common.Event

abstract class BaseViewModel : ViewModel() {

    var isOnLoading = false

    protected val _onBackPressed = MutableLiveData<Event<Unit>>()
    val onBackPressed: LiveData<Event<Unit>> get() = _onBackPressed

    private val _onLoading = MutableLiveData<Event<Unit>>()
    val onLoading: LiveData<Event<Unit>> get() = _onLoading

    private val _onFinishLoading = MutableLiveData<Event<Unit>>()
    val onFinishLoading: LiveData<Event<Unit>> get() = _onFinishLoading

    fun onLoading() {
        isOnLoading = true
        _onLoading.postValue(Event(Unit))
    }

    fun onFinishLoading() {
        isOnLoading = false
        _onFinishLoading.postValue(Event(Unit))
    }

    fun onBackPressed() {
        _onBackPressed.value = Event(Unit)
    }

}