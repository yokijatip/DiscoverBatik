package com.enigma.discoverbatik.view.activity.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.data.local.preferences.TokenPreferences
import com.enigma.discoverbatik.data.local.preferences.dataStore
import com.enigma.discoverbatik.databinding.ActivitySplashBinding
import com.enigma.discoverbatik.view.activity.detail.DetailActivity
import com.enigma.discoverbatik.view.activity.landing.LandingActivity
import com.enigma.discoverbatik.view.activity.main.MainActivity
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {

    private lateinit var splashBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

//        Agar Fullscreen dan tidak ada notification bar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed({

            lifecycleScope.launch {
                val token = getTokenDataStore()
                if (token.isNotEmpty()) {
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                } else {
                    startActivity(
                        Intent(
                            this@SplashActivity,
                            LandingActivity::class.java
                        )
                    )
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                }
                finish()
            }
        }, 2000)
    }

    private suspend fun getTokenDataStore():String {
        val dataStore = TokenPreferences.getInstance(this.dataStore)
        return dataStore.getToken()
    }
}