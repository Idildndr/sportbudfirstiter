package com.example.sportbud3.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.sportbud3.Constants
import com.example.sportbud3.R
import com.example.sportbud3.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // Declare the toolbar as a property of MainActivity
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Find the toolbar by ID and set it as the support action bar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        // Get the username from the intent or bundle
        val username = intent.getStringExtra("LogedUserName")

        // Set the username as the title of the toolbar
        supportActionBar?.title = "Welcome, $username!"


        bottomNavigationView.setupWithNavController(newNavHostFragment.findNavController())

        floatingActionButton.setOnClickListener {
            val intent = Intent(this@MainActivity, CreateRoomActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onBackPressed() {
        // Do nothing to prevent going back to the previous activity
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.log_out -> {
                val intent = Intent(this@MainActivity, LogInActivity::class.java)
                startActivity(intent)
                finishAffinity()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }

}
