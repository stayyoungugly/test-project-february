package com.example.febtestproject.data.response

import com.google.gson.annotations.SerializedName

data class Ratings(
    @SerializedName("0")
    val none: Params,
    @SerializedName("1")
    val one: Params,
    @SerializedName("2")
    val two: Params,
    @SerializedName("3")
    val three: Params,
    @SerializedName("4")
    val four: Params,
    @SerializedName("5")
    val five: Params,
    @SerializedName("6")
    val six: Params,
    @SerializedName("7")
    val seven: Params,
    @SerializedName("8")
    val eight: Params
)
