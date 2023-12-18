package com.enigma.discoverbatik.view.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.enigma.discoverbatik.data.remote.response.PopularBatikResponse
import com.enigma.discoverbatik.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _popularItems = MutableLiveData<List<PopularBatikResponse>>()
    val popularItems: LiveData<List<PopularBatikResponse>> get() = _popularItems

    private val _error = MutableLiveData<String>()

    fun fetchPopularItems() {
        viewModelScope.launch {
            try {
                val response = repository.getAllBatik()
                if (response.isSuccessful) {
                    _popularItems.value = response.body()
                } else {
                    _error.value = "Error : ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Exception: ${e.message}"
            }
        }
    }


}

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}