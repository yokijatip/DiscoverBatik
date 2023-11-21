package com.enigma.discoverbatik.models.adapter.popular

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.enigma.discoverbatik.data.remote.response.ListStoryItem
import com.enigma.discoverbatik.databinding.ListItemPopularBatikBinding

class PopularAdapter :
    PagingDataAdapter<ListStoryItem, PopularAdapter.StoryViewHolder>(DIFF_CALLBACK) {

    inner class StoryViewHolder(private val binding: ListItemPopularBatikBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, storyItem: ListStoryItem) {

            binding.apply {
                Glide.with(itemView)
                    .load(storyItem.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivContentImage)
                tvContentTitle.text = storyItem.name
                tvContentLocation.text = storyItem.createdAt
                tvContentDescription.text = storyItem.description
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoryViewHolder {
        val view =
            ListItemPopularBatikBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null) {
            holder.bind(holder.itemView.context, story)
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}