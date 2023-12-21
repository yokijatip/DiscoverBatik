package com.enigma.discoverbatik.models.adapter.favorite

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enigma.discoverbatik.data.remote.response.FavoriteItem
import com.enigma.discoverbatik.databinding.ListItemPopularBatikSmallCardBinding
import com.enigma.discoverbatik.view.activity.detail.DetailActivity
import com.enigma.discoverbatik.view.fragment.favorite.FavoriteManager

class FavoriteAdapter(private var favoriteItems: List<FavoriteItem>) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemView = ListItemPopularBatikSmallCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(holder.itemView.context, favoriteItems[position])
    }

    override fun getItemCount(): Int {
        return favoriteItems.size
    }

    inner class FavoriteViewHolder(private val binding: ListItemPopularBatikSmallCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, favoriteItem: FavoriteItem) {
            binding.root.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("extra_id", favoriteItem.itemId.toInt())
                context.startActivity(intent)
            }

            binding.apply {
//                    Glide.with(itemView)
//                        .load(favoriteItem.i)
//                        .transition(DrawableTransitionOptions.withCrossFade())
//                        .centerCrop()
//                        .into(ivContentImage)
                tvBatikName.text = favoriteItem.itemName
                tvBatikLocation.text = favoriteItem.itemLocation
                tvBatikDescription.text = favoriteItem.itemDescription

                btnRemove.setOnClickListener {
                    val favoriteManager = FavoriteManager.getInstance()
                    favoriteManager.removeFromFavorites(favoriteItem.itemId)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newFavoriteItems: List<FavoriteItem>) {
        favoriteItems = newFavoriteItems
        notifyDataSetChanged()
    }


}