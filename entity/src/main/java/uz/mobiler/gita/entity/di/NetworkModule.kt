package uz.mobiler.gita.entity.di

import android.content.Context
import android.content.SharedPreferences
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
import uz.mobiler.gita.entity.source.remote.api.CardsApi
import uz.mobiler.gita.entity.source.remote.api.ExchangeApi
import uz.mobiler.gita.entity.source.remote.api.KYCApi
import uz.mobiler.gita.entity.source.remote.api.LoansApi
import uz.mobiler.gita.entity.source.remote.api.PaymentsApi
import uz.mobiler.gita.entity.source.remote.api.TransfersApi
import uz.mobiler.gita.entity.source.remote.api.UsersApi
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
    fun provideRegisterSharedPref(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("register", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideCardsApi(retrofit: Retrofit): CardsApi =
        retrofit.create(CardsApi::class.java)

    @Provides
    @Singleton
    fun provideExchangeApi(retrofit: Retrofit): ExchangeApi =
        retrofit.create(ExchangeApi::class.java)

    @Provides
    @Singleton
    fun provideUsersApi(retrofit: Retrofit): UsersApi =
        retrofit.create(UsersApi::class.java)

    @Provides
    @Singleton
    fun provideTransfersApi(retrofit: Retrofit): TransfersApi =
        retrofit.create(TransfersApi::class.java)

    @Provides
    @Singleton
    fun providePaymentsApi(retrofit: Retrofit): PaymentsApi =
        retrofit.create(PaymentsApi::class.java)

    @Provides
    @Singleton
    fun provideLoansApi(retrofit: Retrofit): LoansApi =
        retrofit.create(LoansApi::class.java)

    @Provides
    @Singleton
    fun provideKycApi(retrofit: Retrofit): KYCApi =
        retrofit.create(KYCApi::class.java)
}