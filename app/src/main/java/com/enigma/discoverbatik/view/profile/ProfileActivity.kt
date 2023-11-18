package com.enigma.discoverbatik.view.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.databinding.ActivityProfileBinding
import com.enigma.discoverbatik.view.explore.ExploreActivity
import com.enigma.discoverbatik.view.favorite.FavoriteActivity
import com.enigma.discoverbatik.view.home.HomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileBinding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(profileBinding.root)

        profileBinding.apply {

        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this@ProfileActivity, HomeActivity::class.java))
                    true
                }

                R.id.explore -> {
                    startActivity(Intent(this@ProfileActivity, ExploreActivity::class.java))
                    true
                }

                R.id.favorite -> {
                    startActivity(Intent(this@ProfileActivity, FavoriteActivity::class.java))
                    true
                }
                else -> false

            }
        }

    }

}