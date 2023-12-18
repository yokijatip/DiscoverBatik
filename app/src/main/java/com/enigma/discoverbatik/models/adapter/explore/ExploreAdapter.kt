package com.enigma.discoverbatik.models.adapter.explore

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.enigma.discoverbatik.data.remote.response.PopularBatikResponse
import com.enigma.discoverbatik.databinding.ListItemExploreBinding


class ExploreAdapter : RecyclerView.Adapter<ExploreAdapter.StoryViewHolder>() {

    private var listItem: List<PopularBatikResponse> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<PopularBatikResponse>) {
        listItem = newList
        notifyDataSetChanged()
    }

    inner class StoryViewHolder(private val binding: ListItemExploreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(popularItem: PopularBatikResponse) {

            binding.apply {
                Glide.with(itemView)
                    .load(popularItem.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivContentImage)
                tvContentTitle.text = popularItem.batikName
                tvContentLocation.text = popularItem.asalDaerah
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