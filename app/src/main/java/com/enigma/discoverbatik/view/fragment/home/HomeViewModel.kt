package com.enigma.discoverbatik.view.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.enigma.discoverbatik.data.remote.response.ListStoryItem
import com.enigma.discoverbatik.data.remote.response.PopularItemResponse
import com.enigma.discoverbatik.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _popularItems = MutableLiveData<PopularItemResponse>()
    val popularItems: LiveData<PopularItemResponse> get() = _popularItems

    fun fetchPopularItems() {
        viewModelScope.launch {
            try {
                val popularItems = repository.getStory()
                _popularItems.value = popularItems
            } catch (e: Exception) {
                throw e
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