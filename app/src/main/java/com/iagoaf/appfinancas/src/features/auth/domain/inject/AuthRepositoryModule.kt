package com.iagoaf.appfinancas.src.features.auth.domain.inject

import com.iagoaf.appfinancas.src.features.auth.domain.repository.IAuthRepository
import com.iagoaf.appfinancas.src.features.auth.infra.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): IAuthRepository
}
