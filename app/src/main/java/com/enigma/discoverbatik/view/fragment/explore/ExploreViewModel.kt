package com.enigma.discoverbatik.view.fragment.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.enigma.discoverbatik.data.remote.response.PopularItemResponse
import com.enigma.discoverbatik.repository.Repository
import kotlinx.coroutines.launch

class ExploreViewModel(private var repository: Repository) : ViewModel() {

    private val _dataItems = MutableLiveData<PopularItemResponse>()
    val dataItems: LiveData<PopularItemResponse> get() = _dataItems

    fun getStudiItem() {
        viewModelScope.launch {
            try {
                val dataItems = repository.getStory()
                _dataItems.value = dataItems
            } catch (e: Exception) {
                throw e
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