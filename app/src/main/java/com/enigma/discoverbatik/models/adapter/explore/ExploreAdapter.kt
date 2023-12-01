package com.enigma.discoverbatik.models.adapter.explore

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.enigma.discoverbatik.data.remote.response.ListStoryItem
import com.enigma.discoverbatik.databinding.ListItemExploreBinding
import com.enigma.discoverbatik.databinding.ListItemPopularBatikSmallCardBinding

class ExploreAdapter : RecyclerView.Adapter<ExploreAdapter.StoryViewHolder>() {

    private var listItem: List<ListStoryItem> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<ListStoryItem>) {
        listItem = newList
        notifyDataSetChanged()
    }

    inner class StoryViewHolder(private val binding: ListItemExploreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(storyItem: ListStoryItem) {

            binding.apply {
                Glide.with(itemView)
                    .load(storyItem.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivContentImage)
                tvContentTitle.text = storyItem.name
                tvContentLocation.text = storyItem.createdAt
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = ListItemExploreBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StoryViewHolder(view)
    }

    override fun getItemCount() = listItem.size

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

}