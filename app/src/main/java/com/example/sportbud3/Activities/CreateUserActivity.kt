package com.example.sportbud3.Activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.VideoView
import androidx.core.content.ContextCompat
import com.example.sportbud3.Constants
import com.example.sportbud3.Model.Contents
import com.example.sportbud3.Model.UserItem
import com.example.sportbud3.R
import com.example.sportbud3.services.RestApiService2
import kotlinx.android.synthetic.main.activity_create_user.*
import kotlinx.coroutines.GlobalScope

class CreateUserActivity : AppCompatActivity() {
    private val selectedButtons = ArrayList<Button>()
    private lateinit var selectedTexts: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        val errorMessage = "Password can not be smaller than 10 char"
        val errorMessageEmail = "Email is not valid"


        val button1 = findViewById<Button>(R.id.buttonRunning)
        button1.setOnClickListener { toggleButton(button1) }

        val button2 = findViewById<Button>(R.id.buttonCardio)
        button2.setOnClickListener { toggleButton(button2) }

        val button3 = findViewById<Button>(R.id.buttonPilates)
        button3.setOnClickListener { toggleButton(button3) }

        val button4 = findViewById<Button>(R.id.buttonYoga)
        button4.setOnClickListener { toggleButton(button4) }

        val videoView = findViewById<VideoView>(R.id.video_view)
        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.signup_video)
        videoView.setVideoURI(videoUri)
        videoView.setOnCompletionListener {
            // When the video playback is completed, start it again
            videoView.start()
        }
        videoView.start()

        continue_button.setOnClickListener {


            if (editTextTextEmail.text.isEmpty() || editTextTextPersonLastName.text.isEmpty() || editTextTextPersonName.text.isEmpty() || editTextTextAboutYou.text.isEmpty() ||
                editTextTextPersonNickName.text.isEmpty()
            ) {
                Toast.makeText(this, "Areas cant leave empty", Toast.LENGTH_SHORT).show()
            } else if (editTextTextPassword.text.toString()
                    .trim() != editTextTextPasswordControl.text.toString().trim()
            ) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else if (editTextTextPassword.text.length < 8) {
                editTextTextPassword.error = errorMessage
            }

            else {
                createUser()
                val intent = Intent(this@CreateUserActivity, LogInActivity::class.java)
                startActivity(intent)
            }


        }
    }

    private fun toggleButton(button: Button) {
        if (selectedButtons.contains(button)) {
            // Button is already selected, so unselect it
            selectedButtons.remove(button)
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_Dark_Gray))
            button.setTextColor(ContextCompat.getColor(this, R.color.neon_yellow))
        } else {
            // Button is not selected, so select it
            selectedButtons.add(button)
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.neon_yellow))
            button.setTextColor(ContextCompat.getColor(this, R.color.theme_Dark_Gray))
        }
        updateList()
    }

    private fun updateList() {
        selectedTexts = ArrayList<String>()
        var cat_id = ""
        for (button in selectedButtons) {


            when (button.text.toString()) {
                "Yoga" -> cat_id = "638e6a894321c700312ff67e"
                "Pilates" -> cat_id = "638e69cd4321c700312ff67b"
                "Cardio" -> cat_id = "638e675d4321c700312ff674"
                "Running" -> cat_id = "638e69544321c700312ff679"
            }

            selectedTexts.add(cat_id)
            Log.d("arralist", selectedTexts.toString())
        }
        // Do something with the selected texts list
    }

    private fun createUser() {
        Constants.GlobalData.fav_arrayList = selectedTexts
        val apiService = RestApiService2()
        val user = UserItem(
            Contents(

                editTextTextAboutYou.text.toString(),
                editTextTextEmail.text.toString(),
                null,
                selectedTexts,
                editTextTextPersonName.text.toString(),
                editTextTextPersonLastName.text.toString(),
                "",
                null,
                editTextTextPersonNickName.text.toString(),
                editTextTextPassword.text.toString(),

                null
            ),
            "",
            0,
            "",
            "",
            "",
            1,
            true,
            ""

        )
        apiService.addUser(user) {
            if (it != null) {
                Toast.makeText(this@CreateUserActivity, "Login successfully", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(this@CreateUserActivity, "Error", Toast.LENGTH_LONG).show()
            }
        }

    }


}