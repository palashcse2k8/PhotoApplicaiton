package com.example.photoapp
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.photoapp.ui.views.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewIsDisplayed() {
        // Verify that the RecyclerView is displayed
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testRecyclerViewHasItems() {
        // Verify that RecyclerView contains at least one item
        onView(withId(R.id.recyclerView))
            .check(matches(hasMinimumChildCount(1)))
    }

    @Test
    fun testRecyclerViewItemContent() {
        // Verify that a RecyclerView item contains specific text
        onView(withText("Photo 1"))
            .check(matches(isDisplayed()))
    }
}
