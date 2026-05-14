package uz.mobiler.gita.entity.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.mobiler.gita.entity.repository.AuthRepository
import uz.mobiler.gita.entity.repository.CardsRepository
import uz.mobiler.gita.entity.repository.ExchangeRepository
import uz.mobiler.gita.entity.repository.TransfersRepository
import uz.mobiler.gita.entity.repository.UsersRepository
import uz.mobiler.gita.entity.repository.impl.AuthRepositoryImpl
import uz.mobiler.gita.entity.repository.impl.CardsRepositoryImpl
import uz.mobiler.gita.entity.repository.impl.ExchangeRepositoryImpl
import uz.mobiler.gita.entity.repository.impl.TransfersRepositoryImpl
import uz.mobiler.gita.entity.repository.impl.UsersRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @[Binds Singleton]
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @[Binds Singleton]
    fun bindCardsRepository(impl: CardsRepositoryImpl): CardsRepository

    @[Binds Singleton]
    fun bindExchangeRepository(impl: ExchangeRepositoryImpl): ExchangeRepository

    @[Binds Singleton]
    fun bindUsersRepository(impl: UsersRepositoryImpl): UsersRepository

    @[Binds Singleton]
    fun bindTransfersRepository(impl: TransfersRepositoryImpl): TransfersRepository
}