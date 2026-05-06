package uz.mobiler.gita.entity.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenProvider: () -> String?
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requiresAuth = request.header("Requires-Auth") == "true"

        if (!requiresAuth) {
            return chain.proceed(request)
        }

        val token = tokenProvider()

        val newRequest = request.newBuilder()
            .removeHeader("Requires-Auth")
            .apply {
                if (!token.isNullOrEmpty()) {
                    addHeader("Authorization", "Bearer $token")
                }
            }
            .build()

        return chain.proceed(newRequest)
    }
}