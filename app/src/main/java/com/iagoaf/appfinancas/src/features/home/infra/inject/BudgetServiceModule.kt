package com.iagoaf.appfinancas.src.features.home.infra.inject

import com.iagoaf.appfinancas.src.features.home.external.service.BudgetServiceImpl
import com.iagoaf.appfinancas.src.features.home.infra.service.IBudgetService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BudgetServiceModule {
    @Binds
    @Singleton
    abstract fun bindBudgetService(
        impl: BudgetServiceImpl
    ): IBudgetService
}