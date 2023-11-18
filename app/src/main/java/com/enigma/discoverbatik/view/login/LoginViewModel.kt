package com.enigma.discoverbatik.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.enigma.discoverbatik.data.remote.response.LoginResponse
import com.enigma.discoverbatik.repository.Repository

class LoginViewModel(private val repository: Repository) : ViewModel() {

    suspend fun login(email: String, password: String): LoginResponse {
        return repository.login(email, password)
    }

}

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}