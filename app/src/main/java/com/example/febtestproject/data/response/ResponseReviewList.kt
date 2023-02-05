package com.example.febtestproject.data.response

import com.google.gson.annotations.SerializedName

data class ResponseReviewList(
    @SerializedName("raitings")
    val ratings: Ratings
)
