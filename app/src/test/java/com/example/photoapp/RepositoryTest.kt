package com.example.photoapp

import com.example.photoapp.models.Album
import com.example.photoapp.models.Photo
import com.example.photoapp.models.User
import com.example.photoapp.network.ApiService
import com.example.photoapp.repository.Repository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class RepositoryTest {
    private lateinit var apiService: ApiService
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        apiService = mockk()
        repository = Repository(apiService)
    }

    @Test
    fun `test getPhotos returns expected data`() = runBlocking {
        // Mocked API response
        val photos = listOf(
            Photo(albumId = 1, id = 1, title = "Photo 1", url = "url1", thumbnailUrl = "thumbnailUrl1"),
            Photo(albumId = 2, id = 2, title = "Photo 2", url = "url2", thumbnailUrl = "thumbnailUrl2")
        )
        coEvery { apiService.getPhotos() } returns photos

        // Call the repository method
        val result = repository.getPhotos()

        // Assert the results
        assertEquals(photos, result)
    }

    @Test
    fun `test getAlbums returns expected data`() = runBlocking {
        val albums = listOf(
            Album(userId = 1, id = 1, title = "Album 1"),
            Album(userId = 2, id = 2, title = "Album 2")
        )
        coEvery { apiService.getAlbums() } returns albums

        val result = repository.getAlbums()

        assertEquals(albums, result)
    }

    @Test
    fun `test getUsers returns expected data`() = runBlocking {
        val users = listOf(
            User(
                id = 1, username = "User1",
                name = "Palash"
            ),
            User(
                id = 2, username = "User2",
                name = "Mosiur"
            )
        )
        coEvery { apiService.getUsers() } returns users

        val result = repository.getUsers()

        assertNotEquals(users, result)
    }
}
