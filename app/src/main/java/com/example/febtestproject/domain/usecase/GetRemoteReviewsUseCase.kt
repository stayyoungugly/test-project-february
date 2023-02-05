package com.example.febtestproject.domain.usecase

import com.example.febtestproject.domain.entity.Review
import com.example.febtestproject.domain.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetRemoteReviewsUseCase(
    private val remoteRepository: RemoteRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): List<Review> {
        return withContext(dispatcher) {
            remoteRepository.getRemoteReviews()
        }
    }
}
