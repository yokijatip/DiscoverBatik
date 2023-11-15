package com.enigma.discoverbatik.view.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.databinding.ActivityRegisterBinding
import com.enigma.discoverbatik.utils.CommonUtils
import com.enigma.discoverbatik.view.landing.LandingActivity
import com.enigma.discoverbatik.view.login.LoginActivity

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        registerBinding.apply {
            btnBack.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LandingActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }

            linearLayout.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }

            btnRegister.setOnClickListener {
                val username = edtUsername.text
                val email = edtEmail.text
                val password = edtPassword.text

                if (username.isNullOrEmpty() && email.isNullOrEmpty() && password.isNullOrEmpty()) {
                    onFailureRegister("Register Failed, please fill all fields")
                } else if (username!!.length > 20) {
                    onFailureRegister("Username too much")
                } else if (password!!.length < 8) {
                    onFailureRegister("Register Failed, password must be 8 character")
                } else {
                    onSuccessRegister("Register was success please login, thank for creating your account here :)")
                }
            }


        }

    }

    private fun onFailureRegister(message: String) {
        CommonUtils.alertError(this@RegisterActivity, message)
    }

    private fun onSuccessRegister(message: String) {
        CommonUtils.alertSuccess(this@RegisterActivity, message)
    }
}