package uz.mobiler.gita.entity.interceptor

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.HttpException
import uz.mobiler.gita.core.SessionEvent
import uz.mobiler.gita.entity.source.local.TokenManager
import uz.mobiler.gita.entity.source.remote.api.AuthApi
import uz.mobiler.gita.entity.source.remote.api.RefreshApiService
import uz.mobiler.gita.entity.source.remote.request.RefreshTokenRequest
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(private val tokenManager: TokenManager) :
    Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.header("Authorization-Retry") != null) {
            SessionEvent.logout()
            return null
        }

        val newToken = runBlocking { refreshToken() } ?: run {
            SessionEvent.logout()
            return null
        }

        return response.request.newBuilder()
            .header("Authorization", "Bearer $newToken")
            .header("Authorization-Retry", "true")
            .build()
    }

    private suspend fun refreshToken(): String? {
        return try {
            val refreshToken = tokenManager.refreshToken

            val response =
                RefreshApiService.instance.refreshToken(RefreshTokenRequest(refreshToken))
            if (response.isSuccessful) {
                val body = response.body()?.data
                tokenManager.accessToken = body?.accessToken.toString()
                tokenManager.refreshToken = body?.refreshToken.toString()
                body?.accessToken
            } else {
                null
            }
        } catch (e: HttpException) {
            if (e.code() == 401) {

            }
            null
        }
    }

}