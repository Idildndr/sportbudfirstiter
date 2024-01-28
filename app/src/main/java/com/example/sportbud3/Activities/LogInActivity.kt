package com.example.sportbud3.Activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sportbud3.Constants
import com.example.sportbud3.Model.UserResponceItem
import com.example.sportbud3.R
import com.example.sportbud3.services.RetrofitInstance
import kotlinx.android.synthetic.main.activity_log_in.*

class LogInActivity : AppCompatActivity() {
    private lateinit var userCredentials: ArrayList<UserResponceItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val videoView = findViewById<VideoView>(R.id.video_view)
        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.login_video)
        videoView.setVideoURI(videoUri)
        videoView.setOnCompletionListener {
            // When the video playback is completed, start it again
            videoView.start()
        }
        videoView.start()

        button2.setOnClickListener {
            signIn()
        }

        text_view_donthaveaccount.setOnClickListener {
            val intent = Intent(this@LogInActivity, CreateUserActivity::class.java)
            startActivity(intent)
        }
    }


    private fun signIn() {
        val intent = Intent(this@LogInActivity, ChatActivity::class.java)
        lifecycleScope.launchWhenCreated {
            val response = RetrofitInstance.retrofit.getAllCredentials()
            if (response.isSuccessful && response.body() != null) {
                userCredentials = response.body()!!.toMutableList() as ArrayList<UserResponceItem>
                var isMatchFound = false // initialize flag variable


                for (user in userCredentials) {
                    if (editTextTextPersonName.text.trim()
                            .toString() == user.contents.email && editTextTextPassword.text.trim()
                            .toString() == user.contents.password
                    ) {

                        Constants.GlobalData.fav_arrayList =
                            (user.contents.favorite_branches as ArrayList<String>?)!!
                        Constants.GlobalData.userID = user._id
                        Constants.GlobalData.userNickname = user.contents.nickname
                        Constants.GlobalData.userName = user.contents.firstname
                        Constants.GlobalData.userLastName = user.contents.lastname
                        Constants.GlobalData.about = user.contents.about

                        intent.putExtra("loggedInUSerID", user._id)

                        val intent1 = Intent(this@LogInActivity, MainActivity::class.java)
                        intent1.putExtra("LogedUserName", user.contents.firstname)

                        startActivity(intent1)
                        isMatchFound = true
                        break

                    } else if (editTextTextPersonName.text.trim()
                            .toString() == user.contents.email || editTextTextPassword.text.trim()
                            .toString() == user.contents.password
                    ) {
                        Toast.makeText(
                            this@LogInActivity,
                            "Password or email incorrect.",
                            Toast.LENGTH_LONG
                        ).show()
                        isMatchFound = true
                        break
                    }
                }
                if (!isMatchFound) {
                    Toast.makeText(
                        this@LogInActivity,
                        "User not found. Please sign up",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        // Do nothing to prevent going back to the previous activity
    }
}