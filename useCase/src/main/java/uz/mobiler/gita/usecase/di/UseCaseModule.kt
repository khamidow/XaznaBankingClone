package uz.mobiler.gita.usecase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.mobiler.gita.usecase.SendOtpUseCase
import uz.mobiler.gita.usecase.SetPinUseCase
import uz.mobiler.gita.usecase.VerifyOtpUseCase
import uz.mobiler.gita.usecase.impl.SendOtpUseCaseImpl
import uz.mobiler.gita.usecase.impl.SetPinUseCaseImpl
import uz.mobiler.gita.usecase.impl.VerifyOtpUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindSendOtpUseCase(impl: SendOtpUseCaseImpl): SendOtpUseCase

    @Binds
    fun bindVerifyOtpUseCase(impl: VerifyOtpUseCaseImpl): VerifyOtpUseCase

    @Binds
    fun bindSetPinUseCase(impl: SetPinUseCaseImpl): SetPinUseCase
}