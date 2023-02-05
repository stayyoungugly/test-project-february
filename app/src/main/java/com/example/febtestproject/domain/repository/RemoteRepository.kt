package com.example.febtestproject.domain.repository

import com.example.febtestproject.domain.entity.Review

interface RemoteRepository {
    suspend fun getRemoteReviews(): List<Review>
}
