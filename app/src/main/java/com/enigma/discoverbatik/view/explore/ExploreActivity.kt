package com.enigma.discoverbatik.view.explore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.databinding.ActivityExploreBinding
import com.enigma.discoverbatik.view.favorite.FavoriteActivity
import com.enigma.discoverbatik.view.home.HomeActivity
import com.enigma.discoverbatik.view.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ExploreActivity : AppCompatActivity() {

    private lateinit var exploreBinding: ActivityExploreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exploreBinding = ActivityExploreBinding.inflate(layoutInflater)
        setContentView(exploreBinding.root)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this@ExploreActivity, HomeActivity::class.java))
                    false
                }

                R.id.favorite -> {
                    startActivity(Intent(this@ExploreActivity, FavoriteActivity::class.java))
                    false
                }

                R.id.profile -> {
                    startActivity(Intent(this@ExploreActivity, ProfileActivity::class.java))
                    false
                }
                else -> false

            }
        }

    }
}