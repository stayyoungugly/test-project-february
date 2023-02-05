package com.example.febtestproject.domain.usecase

import com.example.febtestproject.domain.entity.Review
import com.example.febtestproject.domain.repository.LocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetLocalReviewsUseCase(
    private val localRepository: LocalRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): List<Review> {
        return withContext(dispatcher) {
            localRepository.getLocalReviews()
        }
    }
}
