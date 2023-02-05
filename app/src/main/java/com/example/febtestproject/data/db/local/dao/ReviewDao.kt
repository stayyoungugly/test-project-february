package com.example.febtestproject.data.db.local.dao

import androidx.room.*
import com.example.febtestproject.data.db.local.model.ReviewEntity

@Dao
interface ReviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: ReviewEntity)

    @Delete
    suspend fun deleteReview(review: ReviewEntity)

    @Update
    suspend fun updateReview(review: ReviewEntity)

    @Query("Select * from review order by review_id ASC")
    fun getAllReviews(): List<ReviewEntity>

    @Query("DELETE FROM review")
    suspend fun clearReview()

    @Query("DELETE FROM review WHERE review_id = :id")
    suspend fun deleteReviewById(id: Int)
}
