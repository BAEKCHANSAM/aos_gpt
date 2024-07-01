package com.android.base.views.container.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import api
import com.android.base.util.ChatGPTRequest
import com.android.base.util.ChatGPTResponse
import com.android.base.util.Message
import com.android.base.util.STT
import com.android.base.util.d
import com.android.base.views.base.BaseFragment
import com.android.base.views.container.mainAct.MainViewModel
import com.example.custom_base_project.R
import com.example.custom_base_project.databinding.FragmentHomeBinding
import kotlinx.coroutines.Job
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val layoutRes = R.layout.fragment_home
    override val destinationId = R.id.homeFragment
    override val viewModelClass = HomeViewModel::class.java
    override val actViewModelClass = MainViewModel::class.java

    private var sttScope: Job? = null
    private lateinit var sttModel: STT
    private var textToSpeech: TextToSpeech? = null

    override fun initViews(savedInstanceState: Bundle?) {
        sttModel = STT(context)
        ui.viewDataBinding.viewModel = viewModel
        requestPermission()
        setAlarm()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sttModel = STT(context)
        requestPermission()
    }

    override fun addObservers() {
        viewModel.userRequest.observeEvent {
            onChatGptCall()
        }

        viewModel.clickSpeakStart.observeEvent {
            viewModel.sttTextInput(sttModel)
            d("앍 ${sttModel}")
        }

        viewModel.speakSTTMessage.observe {
            onChatGptCall()
        }
    }

    /** 마이크 퍼미션을 받기 위한 함수 **/
    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                context ?: return,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 거부해도 계속 노출됨. ("다시 묻지 않음" 체크 시 노출 안됨.)
            // 허용은 한 번만 승인되면 그 다음부터 자동으로 허용됨.
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.RECORD_AUDIO), 0
            )
        }
    }

    // 전달받은 string을 발화하는 코드
    private fun playAlarm(string: String) {
        textToSpeech?.speak(string, TextToSpeech.QUEUE_FLUSH, null)
        textToSpeech?.playSilentUtterance(750, TextToSpeech.QUEUE_ADD, null)
    }

    /// Google TTS 초기화 코드
    private fun setAlarm() {
        textToSpeech = TextToSpeech(context, TextToSpeech.OnInitListener {
            if (it == TextToSpeech.SUCCESS) {
                val result = textToSpeech!!.setLanguage(Locale.KOREAN)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    d("TTS", "해당언어는 지원되지 않습니다.")
                    return@OnInitListener
                }
            }
        })
    }

    // API 호출
    private fun onChatGptCall() {
        val messages = listOf(
            Message(role = "user", content = viewModel.speakSTTMessage.value.toString())
        )
        val request = ChatGPTRequest(
            model = "gpt-3.5-turbo",
            messages = messages,
            max_tokens = 300
        )

        // API 호출
        api.getCompletion(request).enqueue(object : Callback<ChatGPTResponse> {
            // API 응답을 성공적으로 받았을 때 호출되는 메서드
            override fun onResponse(
                call: Call<ChatGPTResponse>,
                response: Response<ChatGPTResponse>
            ) {
                // 응답이 성공적일 때
                if (response.isSuccessful) {
                    // 응답 본문을 ChatGPTResponse 객체로 변환
                    val chatResponse = response.body()
                    // 변환된 응답이 null이 아닌지 확인하고, choices 리스트의 첫 번째 항목에서 message의 content를 추출
                    chatResponse?.choices?.get(0)?.message?.content?.let { responseText ->
                        // API Response (HTTP 상태 코드 200-299)
                        d("ChatGPT Response: $responseText")
                        viewModel.inputRequest(responseText)
                        playAlarm(responseText)
                    }
                } else {
                    // API가 오류일 경우 (HTTP 상태 코드 400-599)
                    println("ChatGPT Error: ${response.errorBody()?.string()}")
                    playAlarm("API 오류 입니다.")
                }
            }

            override fun onFailure(call: Call<ChatGPTResponse>, t: Throwable) {
                // API호출에 실패 할 경우
                println("ChatGPT Failure: ${t.message}")
                playAlarm("API 호출에 실패 했습니다.")

            }
        })
    }

}
