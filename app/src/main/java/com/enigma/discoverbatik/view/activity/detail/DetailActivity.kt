package com.enigma.discoverbatik.view.activity.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.databinding.ActivityDetailBinding
import com.enigma.discoverbatik.di.Injection
import com.enigma.discoverbatik.utils.CommonUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    //    State apakah item sudah dimasukkan ke favorite?
    //    dan ini state buat floating action button
    private var isFavorite = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        detailBinding.fabFavorite.setOnClickListener {
            isFavorite = !isFavorite
            updateFabIcon(detailBinding.fabFavorite)
            CommonUtils.showToast(this@DetailActivity, "Favorite Items 😄")
        }

        val repository = Injection.provideRepository(this@DetailActivity)
        val factory = ViewModelFactory(repository)
        detailViewModel =
            ViewModelProvider(this@DetailActivity, factory)[DetailViewModel::class.java]

        getDetailId()
        getDetail()
    }

    private fun getDetail() {
        detailViewModel.batikDetail.observe(this@DetailActivity) { content ->
            if (content != null) {
                detailBinding.apply {
                    Glide.with(this@DetailActivity)
                        .load(content.photoUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivContentImage)
                }
                detailBinding.tvContentTitle.text = content.batikName
                detailBinding.tvContentLocation.text = content.asalDaerah
                detailBinding.tvContentPrice.text = content.price.toString()
                detailBinding.tvContentTechnique.text = content.technique
                detailBinding.tvContentDescription.text = content.content
            }
        }
    }

    private fun getDetailId() {
        val id = intent.getIntExtra("extra_id", -1)
        Log.d("Waduh id", "Id Item : $id")
        detailViewModel.getDetailById(id)
    }

    private fun updateFabIcon(fab: FloatingActionButton) {
        if (isFavorite) {
            fab.setImageResource(R.drawable.ic_heart_filled)
        } else {
            fab.setImageResource(R.drawable.ic_heart)
        }
    }
}