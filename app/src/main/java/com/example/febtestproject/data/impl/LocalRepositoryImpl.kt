package com.example.febtestproject.data.impl

import com.example.febtestproject.data.db.local.dao.ReviewDao
import com.example.febtestproject.data.mapper.ReviewMapper
import com.example.febtestproject.domain.entity.Review
import com.example.febtestproject.domain.repository.LocalRepository

class LocalRepositoryImpl(
    private val reviewDao: ReviewDao,
    private val mapper: ReviewMapper
) : LocalRepository {

    override suspend fun getLocalReviews(): List<Review> =
        mapper.entityListToReviews(reviewDao.getAllReviews())

    override suspend fun insertReview(review: Review) {
        reviewDao.insertReview(mapper.reviewToEntity(review))
    }

    override suspend fun insertListReview(reviews: List<Review>) {
        for (review in reviews) {
            insertReview(review)
        }
    }

    override suspend fun clearReview() = reviewDao.clearReview()
}
