package com.android.base.util

// API 요청에 사용할 데이터 클래스
data class ChatGPTRequest(
    val model: String, // 사용할 모델 이름
    val messages: List<Message>, // 메시지 목록
    val max_tokens: Int // 생성할 최대 토큰 수
)

// 메시지 데이터 클래스
data class Message(
    val role: String, // 메시지의 역할 (예: user, assistant)
    val content: String // 메시지 내용
)

// API 응답을 받을 데이터 클래스
data class ChatGPTResponse(
    val id: String, // 응답 ID
    val choices: List<Choice> // 선택지 목록
)

// 선택지 데이터 클래스
data class Choice(
    val message: Message
)
