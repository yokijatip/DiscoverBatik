package com.enigma.discoverbatik.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.data.local.preferences.TokenPreferences
import com.enigma.discoverbatik.data.local.preferences.dataStore
import com.enigma.discoverbatik.databinding.ActivityLoginBinding
import com.enigma.discoverbatik.di.Injection
import com.enigma.discoverbatik.utils.CommonUtils
import com.enigma.discoverbatik.view.home.HomeActivity
import com.enigma.discoverbatik.view.register.RegisterActivity
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        val injection = Injection.provideRepository(this@LoginActivity)
        val factory = ViewModelFactory(injection)
        loginViewModel = ViewModelProvider(this@LoginActivity, factory)[LoginViewModel::class.java]

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
                login()
            }
        }
    }

    private fun login() {
        val email = loginBinding.edtEmail.text.toString().trim()
        val password = loginBinding.edtPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            onFailureLogin("Login Failed, please fill email and password")
        } else if (password.length < 8) {
            onFailureLogin("Login Failed, password must be 8 character")
        } else {
            CommonUtils.loading(loginBinding.loading, true)
            lifecycleScope.launch {
                try {
                    val result = loginViewModel.login(email, password)
                    val token = result.loginResult?.token
                    if (token != null) {
                        Log.d("Token", "My Token $token")
                        saveTokenDataStore(token)
                    } else {
                        onFailureLogin("Login Failed, error 😞")
                    }
                    CommonUtils.loading(loginBinding.loading, false)
                    onSuccessLogin()
                } catch (e: Exception) {
                    onFailureLogin("Login Failed, email and password is wrong")
                    CommonUtils.loading(loginBinding.loading, false)
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

    private suspend fun saveTokenDataStore(token: String) {
        val dataStore = TokenPreferences.getInstance(this.dataStore)
        dataStore.saveToken(token)
    }

}


