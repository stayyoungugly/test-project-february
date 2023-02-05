package com.example.febtestproject.data.mapper

import com.example.febtestproject.data.db.local.model.ReviewEntity
import com.example.febtestproject.data.response.Params
import com.example.febtestproject.data.response.ResponseReviewList
import com.example.febtestproject.domain.entity.Review

class ReviewMapper {
    private var count = 1

    private fun responseToReview(params: Params): Review {
        val review = Review(
            title = params.title,
            image = params.image,
            rating = 5F,
            id = count
        )
        count++
        return review
    }

    fun responseListToReviews(response: ResponseReviewList): List<Review> {
        val list = ArrayList<Review>()
        list.add(responseToReview(response.ratings.none))
        list.add(responseToReview(response.ratings.one))
        list.add(responseToReview(response.ratings.two))
        list.add(responseToReview(response.ratings.three))
        list.add(responseToReview(response.ratings.four))
        list.add(responseToReview(response.ratings.five))
        list.add(responseToReview(response.ratings.six))
        list.add(responseToReview(response.ratings.seven))
        list.add(responseToReview(response.ratings.eight))
        return list
    }

    fun entityListToReviews(allReviews: List<ReviewEntity>): List<Review> =
        allReviews.map {review -> entityToReview(review)}


    private fun entityToReview(reviewEntity: ReviewEntity): Review =
        Review(
            id = reviewEntity.id,
            title = reviewEntity.title,
            rating = reviewEntity.rating,
            image = reviewEntity.image,
        )

    fun reviewToEntity(review: Review): ReviewEntity =
        ReviewEntity(
            id = review.id,
            title = review.title,
            rating = review.rating,
            image = review.image
        )


}
