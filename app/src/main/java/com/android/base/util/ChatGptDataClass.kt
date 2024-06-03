package com.android.base.util

data class ChatGPTRequest(
    val model: String,
    val messages: List<Message>,
    val max_tokens: Int
)

data class Message(
    val role: String,
    val content: String
)

data class ChatGPTResponse(
    val id: String,
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)
