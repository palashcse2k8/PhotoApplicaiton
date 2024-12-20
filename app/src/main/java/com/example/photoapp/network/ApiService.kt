package com.example.photoapp.network

import com.example.photoapp.models.Album
import com.example.photoapp.models.Photo
import com.example.photoapp.models.User
import com.example.photoapp.utils.Constants
import retrofit2.http.GET

interface ApiService {

    @GET(Constants.ApiEndpoints.PHOTOS)
    suspend fun getPhotos(): List<Photo>

    @GET(Constants.ApiEndpoints.ALBUMS)
    suspend fun getAlbums(): List<Album>

    @GET(Constants.ApiEndpoints.USERS)
    suspend fun getUsers(): List<User>
}
