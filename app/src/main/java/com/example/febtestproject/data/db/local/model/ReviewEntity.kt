package com.example.febtestproject.data.db.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review")
data class ReviewEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "review_id")
    val id: Int,

    @ColumnInfo(name = "review_title")
    val title: String,

    @ColumnInfo(name = "review_image")
    val image: String,

    @ColumnInfo(name = "review_rating")
    val rating: Float
)
