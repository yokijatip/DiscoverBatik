package com.enigma.discoverbatik.view.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.databinding.ActivityLoginBinding
import com.enigma.discoverbatik.utils.CommonUtils
import com.enigma.discoverbatik.view.home.HomeActivity
import com.enigma.discoverbatik.view.register.RegisterActivity

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginBinding.apply {
            btnBack.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                finish()
            }

            linearLayout.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                finish()
            }

            btnLogin.setOnClickListener {
                val email = edtEmail.text
                val password = edtPassword.text

                if (email.isNullOrEmpty() && password.isNullOrEmpty()) {
                    onFailureLogin("Login Failed, please fill email and password")
                } else if (password!!.length < 8) {
                    onFailureLogin("Login Failed, password must be 8 character")
                } else {
                    onSuccessLogin()
                }
            }
        }
    }

    private fun onFailureLogin(message: String) {
        CommonUtils.alertError(this@LoginActivity, message)
    }

    private fun onSuccessLogin() {
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
}


