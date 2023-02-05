package com.example.febtestproject.di

import androidx.databinding.ktx.BuildConfig
import com.example.febtestproject.data.api.Api
import com.example.febtestproject.data.impl.RemoteRepositoryImpl
import com.example.febtestproject.domain.repository.RemoteRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://wowowcleaner.com/"

val getReviewsModule = module {

    factory(named("Review")) {
        OkHttpClient.Builder()
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(
                                HttpLoggingInterceptor.Level.BODY
                            )
                    )
                }
            }
            .build()
    }

    factory(named("ReviewRetrofit")) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get(named("Review")))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    factory<RemoteRepository> {
        (RemoteRepositoryImpl(
            get(named("ReviewRetrofit")),
            get(),
        ))
    }
}
