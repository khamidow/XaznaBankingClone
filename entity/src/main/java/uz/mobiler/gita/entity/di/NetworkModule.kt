package uz.mobiler.gita.entity.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.mobiler.gita.entity.interceptor.AuthInterceptor
import uz.mobiler.gita.entity.interceptor.ChuckerCloneInterceptor
import uz.mobiler.gita.entity.interceptor.TokenAuthenticator
import uz.mobiler.gita.entity.source.local.TokenManager
import uz.mobiler.gita.entity.source.remote.api.AuthApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideOkHTTPClient(
        @ApplicationContext context: Context,
        loggingInterceptor: HttpLoggingInterceptor,
        tokenManager: TokenManager
    ): OkHttpClient = OkHttpClient.Builder()
        .authenticator(TokenAuthenticator(tokenManager))
        .addInterceptor(ChuckerCloneInterceptor(context))
        .addInterceptor(AuthInterceptor {
            tokenManager.accessToken
        })
        .addInterceptor(loggingInterceptor)
//        .addInterceptor(ChuckerInterceptor(context))
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://banking-api.zokirov-mob-dev.uz/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)
}