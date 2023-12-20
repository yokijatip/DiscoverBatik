package com.enigma.discoverbatik.view.activity.login

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
import com.enigma.discoverbatik.view.activity.main.MainActivity
import com.enigma.discoverbatik.view.activity.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        auth = FirebaseAuth.getInstance()

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
            authLoginFirebase(email, password)
        }
    }

    private fun authLoginFirebase(email: String, password: String) {
        CommonUtils.loading(loginBinding.loading, true)
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    user?.getIdToken(true)?.addOnCompleteListener { tokeniD ->
                            if (tokeniD.isSuccessful) {
                                val token = tokeniD.result?.token
                                Log.d("Token JWT", "My JWT Token : $token")
                                lifecycleScope.launch {
                                    if (token != null) {
                                        saveTokenDataStore(token)
                                        CommonUtils.alertError(this@LoginActivity, "My Token : $token")
                                    }
                                }
                                CommonUtils.loading(loginBinding.loading, false)
                                onSuccessLogin()
                            } else {
                                onFailureLogin("Failed to get ID Token : ${tokeniD.exception?.message}")
                            }
                        }
                } else {
                    CommonUtils.loading(loginBinding.loading, false)
                    onFailureLogin("Login Failed : ${it.exception?.message}")
                }
            }
    }

    private fun onFailureLogin(message: String) {
        CommonUtils.alertError(this@LoginActivity, message)
    }

    private fun onSuccessLogin() {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    private suspend fun saveTokenDataStore(token: String) {
        val dataStore = TokenPreferences.getInstance(this.dataStore)
        dataStore.saveToken(token)
    }

}


