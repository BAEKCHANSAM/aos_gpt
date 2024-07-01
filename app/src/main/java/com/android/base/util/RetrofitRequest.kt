import com.android.base.util.ChatGPTApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// API 요청에 인증 헤더를 추가하는 인터셉터 클래스
class RetrofitRequest(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $apiKey")// 인증 헤더 추가
            .build()
        return chain.proceed(request)
    }
}

/// 아래에 있는 키는 공유되어서는 안됩니다.
val client = OkHttpClient.Builder()
    .addInterceptor(RetrofitRequest("-"))// 실제 API 키로 대체
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl("https://api.openai.com/")// OpenAI API의 기본 URL
    .addConverterFactory(GsonConverterFactory.create()) // Gson 컨버터 추가
    .client(client) // 인증 인터셉터가 포함된 클라이언트 추가
    .build()

// Retrofit 인터페이스 생성
val api = retrofit.create(ChatGPTApi::class.java)
