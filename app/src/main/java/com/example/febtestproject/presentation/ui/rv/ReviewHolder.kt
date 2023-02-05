package com.example.febtestproject.presentation.ui.rv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.febtestproject.databinding.ItemReviewBinding
import com.example.febtestproject.domain.entity.Review

class ReviewHolder(
    private val binding: ItemReviewBinding,
    private val glide: RequestManager,
) : RecyclerView.ViewHolder(binding.root) {

    private val options by lazy {
        RequestOptions()
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
    }

    fun bind(item: Review) {
        with(binding) {
            tvTitle.text = item.title
            rtBar.rating = item.rating
            glide.load(item.image)
                .apply(options)
                .into(ivIcon)
        }
    }

    fun updateFields(bundle: Bundle) {
        bundle.run {
            getString("TITLE")?.also {
                binding.tvTitle.text = it
            }
            getFloat("RATING").also {
                binding.rtBar.rating = it
            }
            getString("IMAGE")?.also {
                glide.load(it)
                    .apply(options)
                    .into(binding.ivIcon)
            }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            glide: RequestManager,
        ) =
            ReviewHolder(
                ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                glide,
            )
    }
}
