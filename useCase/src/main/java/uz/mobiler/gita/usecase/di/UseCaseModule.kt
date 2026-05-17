package uz.mobiler.gita.usecase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.mobiler.gita.usecase.ApplyForLoanUseCase
import uz.mobiler.gita.usecase.AttachCardUseCase
import uz.mobiler.gita.usecase.CheckCardUseCase
import uz.mobiler.gita.usecase.ConfirmOtpUseCase
import uz.mobiler.gita.usecase.ConfirmPinUseCase
import uz.mobiler.gita.usecase.DetachCardUseCase
import uz.mobiler.gita.usecase.GetAllLoansUseCase
import uz.mobiler.gita.usecase.GetCardsUseCase
import uz.mobiler.gita.usecase.GetExchangeUseCase
import uz.mobiler.gita.usecase.GetPaymentProvidersUseCase
import uz.mobiler.gita.usecase.GetTransactionHistoryUseCase
import uz.mobiler.gita.usecase.GetUserInfoUseCase
import uz.mobiler.gita.usecase.InitiateTransferUseCase
import uz.mobiler.gita.usecase.KycStatusUseCase
import uz.mobiler.gita.usecase.KycSubmitUseCase
import uz.mobiler.gita.usecase.LogoutUseCase
import uz.mobiler.gita.usecase.MakePaymentUseCase
import uz.mobiler.gita.usecase.RepayLoanUseCase
import uz.mobiler.gita.usecase.SendOtpUseCase
import uz.mobiler.gita.usecase.SetMainCardUseCase
import uz.mobiler.gita.usecase.SetPinUseCase
import uz.mobiler.gita.usecase.UpdateUserNameUseCase
import uz.mobiler.gita.usecase.VerifyOtpUseCase
import uz.mobiler.gita.usecase.impl.ApplyForLoanUseCaseImpl
import uz.mobiler.gita.usecase.impl.AttachCardUseCaseImpl
import uz.mobiler.gita.usecase.impl.CheckCardUseCaseImpl
import uz.mobiler.gita.usecase.impl.ConfirmOtpUseCaseImpl
import uz.mobiler.gita.usecase.impl.ConfirmPinUseCaseImpl
import uz.mobiler.gita.usecase.impl.DetachCardUseCaseImpl
import uz.mobiler.gita.usecase.impl.GetAllLoansUseCaseImpl
import uz.mobiler.gita.usecase.impl.GetCardsUseCaseImpl
import uz.mobiler.gita.usecase.impl.GetExchangeUseCaseImpl
import uz.mobiler.gita.usecase.impl.GetPaymentProvidersUseCaseImpl
import uz.mobiler.gita.usecase.impl.GetTransactionHistoryUseCaseImpl
import uz.mobiler.gita.usecase.impl.GetUserInfoUseCaseImpl
import uz.mobiler.gita.usecase.impl.InitiateTransferUseCaseImpl
import uz.mobiler.gita.usecase.impl.KycStatusUseCaseImpl
import uz.mobiler.gita.usecase.impl.KycSubmitUseCaseImpl
import uz.mobiler.gita.usecase.impl.LogoutUseCaseImpl
import uz.mobiler.gita.usecase.impl.MakePaymentUseCaseImpl
import uz.mobiler.gita.usecase.impl.RepayLoanUseCaseImpl
import uz.mobiler.gita.usecase.impl.SendOtpUseCaseImpl
import uz.mobiler.gita.usecase.impl.SetMainCardUseCaseImpl
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

    @Binds
    fun bindsSetMainCardUseCase(impl: SetMainCardUseCaseImpl): SetMainCardUseCase

    @Binds
    fun bindsInitiateTransferUseCase(impl: InitiateTransferUseCaseImpl): InitiateTransferUseCase

    @Binds
    fun bindsConfirmPinUseCase(impl: ConfirmPinUseCaseImpl): ConfirmPinUseCase

    @Binds
    fun bindsConfirmOtpUseCase(impl: ConfirmOtpUseCaseImpl): ConfirmOtpUseCase

    @Binds
    fun bindsCheckCardUseCase(impl: CheckCardUseCaseImpl): CheckCardUseCase

    @Binds
    fun bindGetPaymentProvidersUseCase(impl: GetPaymentProvidersUseCaseImpl): GetPaymentProvidersUseCase

    @Binds
    fun bindMakePaymentUseCase(impl: MakePaymentUseCaseImpl): MakePaymentUseCase

    @Binds
    fun bindGetAllLoansUseCase(impl: GetAllLoansUseCaseImpl): GetAllLoansUseCase

    @Binds
    fun bindApplyForLoanUseCase(impl: ApplyForLoanUseCaseImpl): ApplyForLoanUseCase

    @Binds
    fun bindRepayLoanUseCase(impl: RepayLoanUseCaseImpl): RepayLoanUseCase

    @Binds
    fun bindGetTransactionHistoryUseCase(impl: GetTransactionHistoryUseCaseImpl): GetTransactionHistoryUseCase

    @Binds
    fun bindKycStatusUseCase(impl: KycStatusUseCaseImpl): KycStatusUseCase

    @Binds
    fun bindKycSubmitUseCase(impl: KycSubmitUseCaseImpl): KycSubmitUseCase
}