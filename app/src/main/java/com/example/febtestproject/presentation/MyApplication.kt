package com.example.febtestproject.presentation

import android.app.Application
import android.content.Context
import com.example.febtestproject.di.getReviewsModule
import com.example.febtestproject.di.repositoryModule
import com.example.febtestproject.di.useCaseModule
import com.example.febtestproject.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        startKoin {
            allowOverride(false)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    viewModelModule,
                    useCaseModule,
                    getReviewsModule,
                    repositoryModule,
                )
            )
        }
    }
}
