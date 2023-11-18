package com.enigma.discoverbatik.view.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.databinding.ActivityFavoriteBinding
import com.enigma.discoverbatik.view.explore.ExploreActivity
import com.enigma.discoverbatik.view.home.HomeActivity
import com.enigma.discoverbatik.view.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteBinding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favoriteBinding.root)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this@FavoriteActivity, HomeActivity::class.java))
                    true
                }

                R.id.explore -> {
                    startActivity(Intent(this@FavoriteActivity, ExploreActivity::class.java))
                    true
                }

                R.id.profile -> {
                    startActivity(Intent(this@FavoriteActivity, ProfileActivity::class.java))
                    true
                }
                else -> false

            }
        }

    }
}