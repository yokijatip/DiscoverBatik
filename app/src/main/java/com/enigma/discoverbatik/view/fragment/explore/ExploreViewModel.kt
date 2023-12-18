package com.enigma.discoverbatik.view.fragment.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.enigma.discoverbatik.data.remote.response.PopularBatikResponse
import com.enigma.discoverbatik.repository.Repository
import kotlinx.coroutines.launch

class ExploreViewModel(private var repository: Repository) : ViewModel() {

    private val _dataItems = MutableLiveData<List<PopularBatikResponse>>()
    val dataItems: LiveData<List<PopularBatikResponse>> get() = _dataItems

    private val _error = MutableLiveData<String>()

    fun getStudiItem() {
        viewModelScope.launch {
            try {
                val response = repository.getAllBatik()
                if (response.isSuccessful) {
                    _dataItems.value = response.body()
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
        if (modelClass.isAssignableFrom(ExploreViewModel::class.java)) {
            return ExploreViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}