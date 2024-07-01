package com.android.base.util

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatGPTApi {
    // HTTP POST 요청을 위한 메서드, Content-Type 헤더를 JSON으로 설정
    @Headers("Content-Type: application/json")
    // OpenAI의 chat completions 엔드포인트
    @POST("v1/chat/completions")
    fun getCompletion(@Body request: ChatGPTRequest): Call<ChatGPTResponse>
}