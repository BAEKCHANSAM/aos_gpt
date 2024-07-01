package com.android.base.views.container.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.base.util.STT
import com.android.base.util.d
import com.android.base.views.base.BaseViewModel
import com.android.base.views.common.Event

class HomeViewModel : BaseViewModel() {

    private val _userInputText = MutableLiveData<String>()
    val userInputText: LiveData<String> get() = _userInputText

    private val _userRequest = MutableLiveData<Event<Unit>>()
    val userRequest: LiveData<Event<Unit>> get() = _userRequest

    private val _clickSpeakStart = MutableLiveData<Event<Unit>>()
    val clickSpeakStart: LiveData<Event<Unit>> get() = _clickSpeakStart

    private val _speakSTTMessage = MutableLiveData<String>()
    val speakSTTMessage: LiveData<String> get() = _speakSTTMessage

    fun onUserRequest() {
        _userRequest.value = Event(Unit)
    }

    fun inputRequest(text: String) {
        _userInputText.value = text
    }

    fun onClickSpeakStart() {
        _clickSpeakStart.value = Event(Unit)
    }

    fun sttTextInput(stt: STT) {
        stt.startListening { recognizedText ->
            _speakSTTMessage.value = recognizedText
            d("앍 STT로 입력된 텍스트 입니다 : ${_speakSTTMessage.value}")
        }

    }

}