//import android.view.View
//import android.view.ViewGroup
//import androidx.appcompat.app.AlertDialog
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.recyclerview.widget.RecyclerView
//import com.example.photoapp.R
//import com.example.photoapp.ui.adapter.PhotoAdapter
//import com.example.photoapp.ui.viewmodel.MainViewModel
//import org.junit.Before
//import org.junit.Test
//import androidx.test.core.app.ActivityScenario
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import org.junit.runner.RunWith
//import androidx.test.espresso.Espresso.*
//import androidx.test.espresso.matcher.ViewMatchers.*
//import androidx.test.espresso.action.ViewActions.*
//import com.example.photoapp.models.Photo
//import com.example.photoapp.ui.views.MainActivity
//import org.junit.Assert.assertEquals
//
//@RunWith(AndroidJUnit4::class)
//class MainActivityTest {
//
//    private lateinit var mainActivityScenario: ActivityScenario<MainActivity>
//    private lateinit var viewModel: MainViewModel
//    private lateinit var mockAdapter: PhotoAdapter
//    private lateinit var mockProgressBar: View
//    private lateinit var mockDataLayout: ViewGroup
//
//    @Before
//    fun setUp() {
//        // Initialize mocks
//        viewModel = mock()
//        mockAdapter = mock()
//        mockProgressBar = mock()
//        mockDataLayout = mock()
//
//        // Initialize the activity with a mock ViewModel
//        mainActivityScenario = ActivityScenario.launch(MainActivity::class.java)
//
//        // Set up the mock ViewModel (mock data)
//        `when`(viewModel.photos).thenReturn(MutableLiveData())
//        `when`(viewModel.isLoading).thenReturn(MutableLiveData())
//        `when`(viewModel.errorMessage).thenReturn(MutableLiveData())
//    }
//
//    @Test
//    fun testFetchPhotos_success() {
//        // Mock success scenario
//        val photoList = listOf(/* mock photo data */)
//        val photosLiveData = MutableLiveData<List<Photo>>() // Replace with your actual Photo class
//        photosLiveData.value = photoList
//        `when`(viewModel.photos).thenReturn(photosLiveData)
//
//        // Start the activity and check if RecyclerView is updated
//        mainActivityScenario.onActivity { activity ->
//            val recyclerView = activity.findViewById<RecyclerView>(R.id.recyclerView)
//            // Validate that the RecyclerView has the correct number of items
//            assertEquals(photoList.size, recyclerView.adapter?.itemCount)
//        }
//    }
//
//    @Test
//    fun testFetchPhotos_empty() {
//        // Mock empty list
//        val emptyList = listOf<Photo>()
//        val emptyLiveData = MutableLiveData<List<Photo>>()
//        emptyLiveData.value = emptyList
//        `when`(viewModel.photos).thenReturn(emptyLiveData)
//
//        mainActivityScenario.onActivity { activity ->
//            // Assert that the error dialog is shown when no photos are available
//            val errorDialog = activity.findViewById<AlertDialog>(R.id.error_dialog)
//            verify(errorDialog).show()
//        }
//    }
//
//    @Test
//    fun testLoadingState() {
//        // Mock loading state
//        val isLoadingLiveData = MutableLiveData<Boolean>()
//        isLoadingLiveData.value = true
//        `when`(viewModel.isLoading).thenReturn(isLoadingLiveData)
//
//        mainActivityScenario.onActivity { activity ->
//            // Check that the progress bar is visible when loading
//            val progressBar = activity.findViewById<View>(R.id.progressBar)
//            assertTrue(progressBar.visibility == View.VISIBLE)
//
//            // Check that the data layout is hidden during loading
//            val dataLayout = activity.findViewById<ViewGroup>(R.id.layout_data)
//            assertTrue(dataLayout.visibility == View.GONE)
//        }
//    }
//
//    @Test
//    fun testMenuReload() {
//        // Simulate clicking the reload menu item
//        onView(withId(R.id.action_reload)).perform(click())
//
//        mainActivityScenario.onActivity { activity ->
//            // Verify that the reloadData method is called when reload is clicked
//            verify(activity, times(1)).reloadData()
//        }
//    }
//
//    @Test
//    fun testErrorDialog_displayedWhenErrorOccurs() {
//        // Simulate error message
//        val errorMessage = "Error fetching data"
//        val errorLiveData = MutableLiveData<String>()
//        errorLiveData.value = errorMessage
//        `when`(viewModel.errorMessage).thenReturn(errorLiveData)
//
//        mainActivityScenario.onActivity { activity ->
//            // Verify that an error dialog is shown with the error message
//            val dialog = activity.findViewById<AlertDialog>(R.id.error_dialog)
//            verify(dialog).setMessage(errorMessage)
//        }
//    }
//
//    @Test
//    fun testToolbarMenuPrepared() {
//        // Simulate onPrepareOptionsMenu
//        mainActivityScenario.onActivity { activity ->
//            val menu = activity.menu
//            assertNotNull(menu)
//
//            // Ensure menu item icons are tinted correctly
//            for (i in 0 until menu.size()) {
//                val menuItem = menu.getItem(i)
//                assertNotNull(menuItem.icon)
//                assertEquals(menuItem.icon.tint, activity.resources.getColor(android.R.color.white, null))
//            }
//        }
//    }
//}
