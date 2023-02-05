package com.example.febtestproject.data.impl

import com.example.febtestproject.data.api.Api
import com.example.febtestproject.data.mapper.ReviewMapper
import com.example.febtestproject.domain.entity.Review
import com.example.febtestproject.domain.repository.RemoteRepository

class RemoteRepositoryImpl(
    private val api: Api,
    private val mapper: ReviewMapper
) : RemoteRepository {
    override suspend fun getRemoteReviews(): List<Review> {
        return mapper.responseListToReviews(api.getReviews())
    }
}
