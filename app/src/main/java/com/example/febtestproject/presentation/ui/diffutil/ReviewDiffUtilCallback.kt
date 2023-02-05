package com.example.febtestproject.presentation.ui.diffutil

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.example.febtestproject.domain.entity.Review

class ReviewDiffUtilCallback : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(oldItem: Review, newItem: Review) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Review, newItem: Review) =
        oldItem.title == newItem.title

    override fun getChangePayload(oldItem: Review, newItem: Review): Any? {
        val bundle = Bundle()
        if (oldItem.title != newItem.title) {
            bundle.putString("TITLE", newItem.title)
        }
        if (oldItem.image != newItem.image) {
            bundle.putString("IMAGE", newItem.image)
        }
        if (oldItem.rating != newItem.rating) {
            bundle.putFloat("RATING", newItem.rating)
        }

        if (bundle.isEmpty) return null
        return bundle
    }
}
