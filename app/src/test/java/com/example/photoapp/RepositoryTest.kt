package com.example.photoapp.repository

import com.example.photoapp.models.Album
import com.example.photoapp.models.Photo
import com.example.photoapp.models.User
import com.example.photoapp.network.ApiService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*


class RepositoryTest {

    private lateinit var repository: Repository
    private val mockApiService: ApiService = mock(ApiService::class.java)

    @Before
    fun setUp() {
        repository = Repository(mockApiService)
    }

    @Test
    fun `getPhotos should fetch photos from API`(): Unit = runBlocking {
        val mockPhotos = listOf(
            Photo(
                id = 1, albumId = 1, title = "Photo 1", thumbnailUrl = "url1",
                url = "url2"
            )
        )
        `when`(mockApiService.getPhotos()).thenReturn(mockPhotos)

        val result = repository.getPhotos()

        assertEquals(mockPhotos, result)
        verify(mockApiService).getPhotos()
    }

    @Test
    fun `getAlbums should fetch albums from API`(): Unit = runBlocking {
        val mockAlbums = listOf(Album(id = 1, userId = 1, title = "Album 1"))
        `when`(mockApiService.getAlbums()).thenReturn(mockAlbums)

        val result = repository.getAlbums()

        assertEquals(mockAlbums, result)
        verify(mockApiService).getAlbums()
    }

    @Test
    fun `getUsers should fetch users from API`(): Unit = runBlocking {
        val mockUsers = listOf(User(
            id = 1, username = "User 1",
            name = "Palash"
        ))
        `when`(mockApiService.getUsers()).thenReturn(mockUsers)

        val result = repository.getUsers()

        assertEquals(mockUsers, result)
        verify(mockApiService).getUsers()
    }
}
