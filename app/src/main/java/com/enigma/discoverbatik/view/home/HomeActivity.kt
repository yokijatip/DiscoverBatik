package com.enigma.discoverbatik.view.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.data.local.preferences.TokenPreferences
import com.enigma.discoverbatik.data.local.preferences.dataStore
import com.enigma.discoverbatik.databinding.ActivityHomeBinding
import com.enigma.discoverbatik.view.explore.ExploreActivity
import com.enigma.discoverbatik.view.favorite.FavoriteActivity
import com.enigma.discoverbatik.view.landing.LandingActivity
import com.enigma.discoverbatik.view.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class HomeActivity : AppCompatActivity() {

    private lateinit var homeBinding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_home)

        homeBinding.apply {
            lifecycleScope.launch {
                val token = getToken()
                tvToken.text = token
            }
            btnLogout.setOnClickListener {
                lifecycleScope.launch {
                    clearToken()
                    startActivity(Intent(this@HomeActivity, LandingActivity::class.java))
                    finish()
                }
            }

            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

            bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home -> {
                        startActivity(Intent(this@HomeActivity, HomeActivity::class.java))
                        true
                    }

                    R.id.explore -> {
                        startActivity(Intent(this@HomeActivity, ExploreActivity::class.java))
                        true
                    }

                    R.id.favorite -> {
                        startActivity(Intent(this@HomeActivity, FavoriteActivity::class.java))
                        true
                    }

                    R.id.profile -> {
                        startActivity(Intent(this@HomeActivity, ProfileActivity::class.java))
                        true
                    }
                    else -> false

                }
            }

        }

    }

    private suspend fun getToken(): String {
        val dataStore = TokenPreferences.getInstance(this.dataStore)
        return dataStore.getToken()
    }

    private suspend fun clearToken() {
        val dataStore = TokenPreferences.getInstance(this.dataStore)
        return dataStore.clearToken()
    }
}