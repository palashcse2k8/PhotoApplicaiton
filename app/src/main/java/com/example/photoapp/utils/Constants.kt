package com.example.photoapp.utils

object Constants {
    const val BASE_URL: String = "https://jsonplaceholder.typicode.com/"
    const val TIMEOUT_DURATION: Long = 30L // Timeout duration in seconds
    const val PHOTO_THUMBNAIL_SIZE: Int = 150 // Thumbnail size in pixels
    const val API_DATE_FORMAT: String = "yyyy-MM-dd'T'HH:mm:ss'Z'" // Date format for parsing API responses

    object ErrorMessages {
        const val NETWORK_ERROR: String = "Unable to connect to the server."
        const val GENERIC_ERROR: String = "Something went wrong. Please try again later."
    }

    object ApiEndpoints {
        const val PHOTOS: String = "photos"
        const val USERS: String = "users"
        const val ALBUMS: String = "albums"
    }
}
