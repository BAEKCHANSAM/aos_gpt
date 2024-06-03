package com.android.base.views.container.home

import android.os.Bundle
import api
import com.android.base.util.ChatGPTRequest
import com.android.base.util.ChatGPTResponse
import com.android.base.util.Message
import com.android.base.util.d
import com.android.base.views.base.BaseFragment
import com.android.base.views.container.mainAct.MainViewModel
import com.example.custom_base_project.R
import com.example.custom_base_project.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val layoutRes = R.layout.fragment_home
    override val destinationId = R.id.homeFragment
    override val viewModelClass = HomeViewModel::class.java
    override val actViewModelClass = MainViewModel::class.java

    override fun initViews(savedInstanceState: Bundle?) {
        ui.viewDataBinding.viewModel = viewModel
    }

    override fun addObservers() {
        viewModel.userRequest.observeEvent {
            onChatGptCall()
        }
    }

    private fun onChatGptCall() {
        // API 호출
        val messages = listOf(
            Message(role = "user", content = "서울에 대해 알려줘")
        )
        val request = ChatGPTRequest(
            model = "gpt-3.5-turbo",
            messages = messages,
            max_tokens = 50
        )

        api.getCompletion(request).enqueue(object : Callback<ChatGPTResponse> {
            override fun onResponse(
                call: Call<ChatGPTResponse>,
                response: Response<ChatGPTResponse>
            ) {
                if (response.isSuccessful) {
                    val chatResponse = response.body()
                    chatResponse?.choices?.get(0)?.message?.content?.let { responseText ->
                        // Handle the response text
                        d("채찍피티 Response: $responseText")
                    }
                } else {
                    // Handle API error
                    println("채찍피티 Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ChatGPTResponse>, t: Throwable) {
                // Handle network error
                println("채찍피티 Failure: ${t.message}")
            }
        })
    }
}


