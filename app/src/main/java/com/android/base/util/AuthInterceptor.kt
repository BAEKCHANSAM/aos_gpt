import com.android.base.util.ChatGPTApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $apiKey")
            .build()
        return chain.proceed(request)
    }
}

/// 아래에 있는 키는 공유되어서는 안됩니다.
val client = OkHttpClient.Builder()
    .addInterceptor(AuthInterceptor("---"))
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl("https://api.openai.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

val api = retrofit.create(ChatGPTApi::class.java)
