package uz.mobiler.gita.entity.interceptor

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import uz.mobiler.gita.entity.notificationHelper.NotificationHelper
import javax.inject.Inject

class ChuckerCloneInterceptor @Inject constructor(@ApplicationContext val context: Context) :
    Interceptor {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requestTime = System.currentTimeMillis()
        val requestLog = buildString {
            appendLine("Request")
            appendLine("Url: ${request.url}")
            appendLine("Method: ${request.method}")
            appendLine("Headers: ")
            request.headers.forEach { (name, value) ->
                appendLine("$name: $value")
            }

            request.body?.let { body ->
                val buffer = Buffer()
                body.writeTo(buffer)
                appendLine("Body: ${buffer.readUtf8()}")
            } ?: appendLine("Body: null")
        }

        NotificationHelper.createNotification(
            context,
            requestLog
        )

        val response = chain.proceed(request)
        val responseTime = System.currentTimeMillis()

        val responseBodyString = response.body.string()

        val responseLog = buildString {
            appendLine("Response")
            appendLine("Url: ${response.request.url}")
            appendLine("Code: ${response.code}")
            appendLine("Time: ${responseTime - requestTime}ms")
//            appendLine("Headers:")
//            response.headers.forEach { (name, value) ->
//                appendLine("   $name: $value")
//            }
            appendLine("Body: $responseBodyString")
        }

        NotificationHelper.createNotification(
            context,
            responseLog
        )
        val newBody = responseBodyString.toResponseBody(response.body.contentType())
        return response.newBuilder().body(newBody).build()
    }
}