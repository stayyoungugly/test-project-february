package com.example.febtestproject.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.example.febtestproject.presentation.viewmodel.SecondFragmentViewModel

val viewModelModule = module {
    viewModelOf(::SecondFragmentViewModel)
}
