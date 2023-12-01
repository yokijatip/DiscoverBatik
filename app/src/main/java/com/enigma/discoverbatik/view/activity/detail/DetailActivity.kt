package com.enigma.discoverbatik.view.activity.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.databinding.ActivityDetailBinding
import com.enigma.discoverbatik.utils.CommonUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding

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


    }

    private fun updateFabIcon(fab: FloatingActionButton) {
        if (isFavorite) {
            fab.setImageResource(R.drawable.ic_heart_filled)
        } else {
            fab.setImageResource(R.drawable.ic_heart)
        }
    }
}