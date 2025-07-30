package com.example.test

import com.example.test.multiple_lang.data.repository.FirebaseUserRepository
import com.example.test.multiple_lang.domain.repository.UserRepository
import com.example.test.multiple_lang.domain.usecase.RegisterUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideUserRepository(): UserRepository = FirebaseUserRepository()
    @Provides
    fun provideRegisterUseCase(repo: UserRepository) = RegisterUserUseCase(repo)
}