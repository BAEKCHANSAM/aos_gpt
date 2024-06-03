package com.android.base.views.container.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.base.views.base.BaseViewModel
import com.android.base.views.common.Event

class HomeViewModel : BaseViewModel() {

    private val _userInputText = MutableLiveData<Event<Unit>>()
    val userInputText: LiveData<Event<Unit>> get() = _userInputText

    private val _userRequest = MutableLiveData<Event<Unit>>()
    val userRequest: LiveData<Event<Unit>> get() = _userRequest

    fun inputUserRequest() {
        _userRequest.value = Event(Unit)
    }



}