package com.example.febtestproject.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import com.example.febtestproject.domain.usecase.GetLocalReviewsUseCase
import com.example.febtestproject.domain.usecase.GetRemoteReviewsUseCase
import com.example.febtestproject.domain.usecase.SaveLocalReviewsUseCase

val useCaseModule = module {
    factoryOf(::GetRemoteReviewsUseCase)
    factoryOf(::GetLocalReviewsUseCase)
    factoryOf(::SaveLocalReviewsUseCase)

    factory { Dispatchers.Default }
}
