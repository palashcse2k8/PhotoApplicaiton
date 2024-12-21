package com.example.photoapp.ui.views

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photoapp.R
import com.example.photoapp.ui.adapter.PhotoAdapter
import com.example.photoapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: View
    private lateinit var dataLayout: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        dataLayout = findViewById(R.id.layout_data)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val adapter = PhotoAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        // Observe LiveData for photos
        viewModel.photos.observe(this) { photos ->
            if (photos.isNullOrEmpty()) {
                showErrorDialog("No photos to display.")
            } else {
                adapter.updateData(photos)
            } // Ensure the adapter updates its data
        }

        // Observe LiveData for loading state
        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            dataLayout.visibility = if (isLoading) View.GONE else View.VISIBLE
        }

        // Observe LiveData for error state
        viewModel.errorMessage.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                showErrorDialog(errorMessage)
            }
        }

        // Trigger data fetch
        viewModel.fetchPhotos()
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reload -> {
                reloadData() // Trigger the data fetching
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            for (i in 0 until it.size()) {
                val menuItem = it.getItem(i)
                menuItem.icon?.setTint(resources.getColor(android.R.color.white, theme))
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    private fun reloadData() {
        viewModel.fetchPhotos() // Re-trigger the API call
    }
}