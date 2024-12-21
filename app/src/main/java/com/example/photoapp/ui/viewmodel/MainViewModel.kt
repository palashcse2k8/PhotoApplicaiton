package com.example.photoapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoapp.repository.Repository
import com.example.photoapp.ui.models.ListItemDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _photos = MutableLiveData<List<ListItemDataModel>>()
    val photos: LiveData<List<ListItemDataModel>> get() = _photos

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun fetchPhotos() {
        // Ensure this is executed on the main thread
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val photos = repository.getPhotos()
                val albums = repository.getAlbums()
                val users = repository.getUsers()

                val albumMap = albums.associateBy { it.id }

                val albumUserMap = albums.associateBy { it.id }.mapValues {
                    users.find { user -> user.id == it.value.userId }?.username.orEmpty()
                }

                val listItems = photos.groupBy { it.albumId }.map{ photo ->
                    val album = albumMap[photo.value.first().albumId]
                    val userName = albumUserMap[photo.value.first().albumId].orEmpty()
                    ListItemDataModel(
                        photoTitle = photo.value.first().title,                // Photo title
                        albumTitle = album?.title.orEmpty(),     // Album title
                        userName = userName,                     // User name
                        thumbnailUrl = photo.value.first().thumbnailUrl        // Photo thumbnail URL
                    )
                }

                // Safely update LiveData from the background thread
                _photos.postValue(listItems)

            } catch (e: Exception) {
                // Safely update LiveData from the background thread
                _errorMessage.postValue("Failed to load data: ${e.message}")
            } finally {
                // Safely update LiveData from the background thread
                _isLoading.postValue(false)
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
