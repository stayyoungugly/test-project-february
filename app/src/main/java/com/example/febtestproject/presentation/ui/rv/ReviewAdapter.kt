package com.example.febtestproject.presentation.ui.rv

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import com.example.febtestproject.domain.entity.Review
import com.example.febtestproject.presentation.ui.diffutil.ReviewDiffUtilCallback

class ReviewAdapter(
    private val glide: RequestManager,
) :
    ListAdapter<Review, ReviewHolder>(ReviewDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder =
        ReviewHolder.create(parent, glide)

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: ReviewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            payloads.last().takeIf { it is Bundle }?.let {
                holder.updateFields(it as Bundle)
            }
        }
    }

    override fun submitList(list: MutableList<Review>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }
}
