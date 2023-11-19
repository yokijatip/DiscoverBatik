package com.enigma.discoverbatik.view.activity.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.databinding.ActivityRegisterBinding
import com.enigma.discoverbatik.di.Injection
import com.enigma.discoverbatik.utils.CommonUtils
import com.enigma.discoverbatik.view.activity.landing.LandingActivity
import com.enigma.discoverbatik.view.activity.login.LoginActivity
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        val injection = Injection.provideRepository(this@RegisterActivity)
        val factory = ViewModelFactory(injection)
        registerViewModel =
            ViewModelProvider(this@RegisterActivity, factory)[RegisterViewModel::class.java]

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
                register()
            }

        }

    }

    private fun register() {
        val username = registerBinding.edtUsername.text.toString().trim()
        val email = registerBinding.edtEmail.text.toString().trim()
        val password = registerBinding.edtPassword.text.toString().trim()

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            onFailureRegister("Register Failed, please fill username,email and password")
        } else if (password.length < 8) {
            onFailureRegister("Register Failed, password must be 8 character")
        } else {
            CommonUtils.loading(registerBinding.loading, true)
            lifecycleScope.launch {
                try {
                    registerViewModel.register(username, email, password)
                    CommonUtils.loading(registerBinding.loading, false)
                    onSuccessRegister("Register was success please login, thank for creating your account here :)")
                } catch (e: Exception) {
                    CommonUtils.loading(registerBinding.loading, false)
                    onFailureRegister("Register Failed, username, email & password maybe has already use by another")
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