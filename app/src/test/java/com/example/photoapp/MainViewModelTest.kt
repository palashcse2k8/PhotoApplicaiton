package com.example.photoapp
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.photoapp.models.*
import com.example.photoapp.repository.Repository
import com.example.photoapp.ui.models.ListItemDataModel
import com.example.photoapp.ui.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private val mockRepository: Repository = mock(Repository::class.java)

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchPhotos should populate photos LiveData`() = runTest {
        // Mock API responses
        val mockPhotos = listOf(Photo(
            id = 1, albumId = 1, title = "Photo 1", thumbnailUrl = "url1",
            url = TODO()
        ))
        val mockAlbums = listOf(Album(id = 1, userId = 1, title = "Album 1"))
        val mockUsers = listOf(User(
            id = 1, username = "User 1",
            name = TODO()
        ))

        `when`(mockRepository.getPhotos()).thenReturn(mockPhotos)
        `when`(mockRepository.getAlbums()).thenReturn(mockAlbums)
        `when`(mockRepository.getUsers()).thenReturn(mockUsers)

        val observer: Observer<List<ListItemDataModel>> = mock()
        viewModel.photos.observeForever(observer)

        viewModel.fetchPhotos()
        advanceUntilIdle() // Advance coroutine execution

        val expectedList = listOf(
            ListItemDataModel(
                photoTitle = "Photo 1",
                albumTitle = "Album 1",
                userName = "User 1",
                thumbnailUrl = "url1"
            )
        )

        verify(observer).onChanged(expectedList)
        viewModel.photos.removeObserver(observer)
    }

    @Test
    fun `fetchPhotos should populate errorMessage on failure`() = runTest {
        val observer: Observer<String?> = mock()
        viewModel.errorMessage.observeForever(observer)

        `when`(mockRepository.getPhotos()).thenThrow(RuntimeException("API Error"))
        viewModel.fetchPhotos()
        advanceUntilIdle()

        verify(observer).onChanged("Failed to load data: API Error")
        viewModel.errorMessage.removeObserver(observer)
    }

    @Test
    fun `clearError should set errorMessage to null`() {
        viewModel.clearError()
        Assert.assertNull(viewModel.errorMessage.value)
    }
}
