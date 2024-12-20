package com.example.photoapp.repository

import com.example.photoapp.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val apiService: ApiService) {
    suspend fun getPhotos() = apiService.getPhotos()
    suspend fun getAlbums() = apiService.getAlbums()
    suspend fun getUsers() = apiService.getUsers()
}
