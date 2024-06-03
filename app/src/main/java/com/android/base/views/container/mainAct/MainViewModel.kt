package com.android.base.views.container.mainAct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.base.views.base.BaseViewModel
import com.android.base.views.common.Event

class MainViewModel() :BaseViewModel() {

    private val _test = MutableLiveData<Event<Unit>>()
    val test:LiveData<Event<Unit>> get() = _test

    fun init(){}

    fun onTest(){
        _test.value = Event(Unit)
    }

}