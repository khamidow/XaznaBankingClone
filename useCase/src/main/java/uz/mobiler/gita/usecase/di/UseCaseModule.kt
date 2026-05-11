package uz.mobiler.gita.usecase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.mobiler.gita.usecase.AttachCardUseCase
import uz.mobiler.gita.usecase.DetachCardUseCase
import uz.mobiler.gita.usecase.GetCardsUseCase
import uz.mobiler.gita.usecase.GetExchangeUseCase
import uz.mobiler.gita.usecase.GetUserInfoUseCase
import uz.mobiler.gita.usecase.LogoutUseCase
import uz.mobiler.gita.usecase.SendOtpUseCase
import uz.mobiler.gita.usecase.SetPinUseCase
import uz.mobiler.gita.usecase.UpdateUserNameUseCase
import uz.mobiler.gita.usecase.VerifyOtpUseCase
import uz.mobiler.gita.usecase.impl.AttachCardUseCaseImpl
import uz.mobiler.gita.usecase.impl.DetachCardUseCaseImpl
import uz.mobiler.gita.usecase.impl.GetCardsUseCaseImpl
import uz.mobiler.gita.usecase.impl.GetExchangeUseCaseImpl
import uz.mobiler.gita.usecase.impl.GetUserInfoUseCaseImpl
import uz.mobiler.gita.usecase.impl.LogoutUseCaseImpl
import uz.mobiler.gita.usecase.impl.SendOtpUseCaseImpl
import uz.mobiler.gita.usecase.impl.SetPinUseCaseImpl
import uz.mobiler.gita.usecase.impl.UpdateUserNameUseCaseImpl
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

    @Binds
    fun bindsLogoutUseCase(impl: LogoutUseCaseImpl): LogoutUseCase

    @Binds
    fun bindsAttachCardUseCase(impl: AttachCardUseCaseImpl): AttachCardUseCase

    @Binds
    fun bindsGetCardsUseCase(impl: GetCardsUseCaseImpl): GetCardsUseCase

    @Binds
    fun bindsGetExchangeUseCase(impl: GetExchangeUseCaseImpl): GetExchangeUseCase

    @Binds
    fun bindsGetUserInfoUseCase(impl: GetUserInfoUseCaseImpl): GetUserInfoUseCase

    @Binds
    fun bindsUpdateUserNameUseCase(impl: UpdateUserNameUseCaseImpl): UpdateUserNameUseCase

    @Binds
    fun bindsDetachCardUseCase(impl: DetachCardUseCaseImpl): DetachCardUseCase

}