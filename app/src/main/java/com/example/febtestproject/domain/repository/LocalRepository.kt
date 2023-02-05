package com.example.febtestproject.domain.repository

import com.example.febtestproject.domain.entity.Review

interface LocalRepository {
    suspend fun getLocalReviews(): List<Review>

    suspend fun insertReview(review: Review)

    suspend fun insertListReview(reviews: List<Review>)

    suspend fun clearReview()
}
