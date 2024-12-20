package com.example.photoapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoapp.models.Photo
import com.example.photoapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>> get() = _photos

    fun fetchPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            val photos = repository.getPhotos()
            val albums = repository.getAlbums()
            val users = repository.getUsers()

            val albumUserMap = albums.associateBy { it.id }.mapValues {
                users.find { user -> user.id == it.value.userId }?.username.orEmpty()
            }

            val uniquePhotos = photos.groupBy { it.albumId }.map { entry ->
                val firstPhoto = entry.value.first()
                firstPhoto.copy(
                    title = "${firstPhoto.title} (Album: ${entry.key}, User: ${albumUserMap[entry.key]})"
                )
            }
            _photos.postValue(uniquePhotos)
        }
    }
}
