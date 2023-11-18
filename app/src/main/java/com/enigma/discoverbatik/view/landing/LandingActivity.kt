package com.enigma.discoverbatik.view.landing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.databinding.ActivityLandingBinding
import com.enigma.discoverbatik.view.login.LoginActivity
import com.enigma.discoverbatik.view.register.RegisterActivity


@Suppress("DEPRECATION")
class LandingActivity : AppCompatActivity() {

    private lateinit var landingBinding: ActivityLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        landingBinding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(landingBinding.root)

        landingBinding.apply {

            btnRegister.setOnClickListener {
                startActivity(Intent(this@LandingActivity, RegisterActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }

            linearLayout.setOnClickListener {
                startActivity(Intent(this@LandingActivity, LoginActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }

        }


    }
}