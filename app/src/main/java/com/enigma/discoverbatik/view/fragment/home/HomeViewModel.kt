package com.enigma.discoverbatik.view.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.enigma.discoverbatik.data.remote.response.ListStoryItem
import com.enigma.discoverbatik.repository.Repository

class HomeViewModel(repository: Repository) : ViewModel() {

    val getStory: LiveData<PagingData<ListStoryItem>> =
        repository.getItemPopular().cachedIn(viewModelScope)


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