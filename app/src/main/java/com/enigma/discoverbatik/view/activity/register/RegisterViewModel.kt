package com.enigma.discoverbatik.view.activity.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.enigma.discoverbatik.data.remote.response.RegisterResponse
import com.enigma.discoverbatik.repository.Repository

class RegisterViewModel(private val repository: Repository): ViewModel() {

    suspend fun register(username: String, email: String, password: String): RegisterResponse {
        return repository.register(username, email, password)
    }

}

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}