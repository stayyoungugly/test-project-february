package com.example.febtestproject.data.api

import com.example.febtestproject.data.response.ResponseReviewList
import retrofit2.http.GET

interface Api {
    @GET("testAndroidData")
    suspend fun getReviews(): ResponseReviewList
}
