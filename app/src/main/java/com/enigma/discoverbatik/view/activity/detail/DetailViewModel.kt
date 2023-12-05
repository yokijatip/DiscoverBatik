package com.enigma.discoverbatik.view.activity.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.enigma.discoverbatik.data.remote.response.Story
import com.enigma.discoverbatik.repository.Repository
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository) : ViewModel() {

    private val liveData = MutableLiveData<Story?>()
    fun getDetailLiveData(): MutableLiveData<Story?> {
        return liveData
    }

    fun getDetailById(id: String) {
        viewModelScope.launch {
            try {
                val response = repository.getDetailById(id)
                val detail = response.story
                liveData.value = detail
            } catch (e: Exception) {
                throw e
            }
        }
    }

}

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}