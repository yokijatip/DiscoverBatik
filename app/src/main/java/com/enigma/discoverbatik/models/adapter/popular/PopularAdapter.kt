package com.enigma.discoverbatik.models.adapter.popular

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.enigma.discoverbatik.data.remote.response.PopularBatikResponse
import com.enigma.discoverbatik.databinding.ListItemPopularBatikBinding
import com.enigma.discoverbatik.view.activity.detail.DetailActivity

class PopularAdapter : RecyclerView.Adapter<PopularAdapter.StoryViewHolder>() {

    private var listItem: List<PopularBatikResponse> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<PopularBatikResponse>) {
        listItem = newList
        notifyDataSetChanged()
    }

    inner class StoryViewHolder(private val binding: ListItemPopularBatikBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context,popularItem: PopularBatikResponse) {

            binding.root.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("extra_id", popularItem.id)
                context.startActivity(intent)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(popularItem.link)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivContentImage)
                tvContentTitle.text = popularItem.batikName
                tvContentLocation.text = popularItem.asalDaerah
                tvContentDescription.text = popularItem.content
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = ListItemPopularBatikBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StoryViewHolder(view)
    }

    override fun getItemCount() = listItem.size

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(holder.itemView.context,listItem[position])
    }

}