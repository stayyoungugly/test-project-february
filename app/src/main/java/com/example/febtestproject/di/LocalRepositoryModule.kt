package com.example.febtestproject.di

import android.app.Application
import androidx.room.Room
import com.example.febtestproject.data.db.local.dao.ReviewDao
import com.example.febtestproject.data.db.local.room.ReviewDatabase
import com.example.febtestproject.data.impl.LocalRepositoryImpl
import com.example.febtestproject.data.mapper.ReviewMapper
import com.example.febtestproject.domain.repository.LocalRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::LocalRepositoryImpl) { bind<LocalRepository>() }
    factoryOf(::ReviewMapper)

    fun provideDatabase(application: Application): ReviewDatabase {
        return Room.databaseBuilder(application, ReviewDatabase::class.java, "review")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideReviewDao(database: ReviewDatabase): ReviewDao {
        return database.getReviewDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideReviewDao(get()) }

}

