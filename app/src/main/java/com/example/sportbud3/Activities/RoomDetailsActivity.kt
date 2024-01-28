package com.example.sportbud3.Activities

import  android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.sportbud3.Adapters.UserAdapter
import com.example.sportbud3.Constants
import com.example.sportbud3.R
import com.example.sportbud3.databinding.ActivityRoomDetailsBinding
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_room_details.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible

class RoomDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoomDetailsBinding
    private lateinit var userListAdapter: UserAdapter
    private lateinit var activityIdFromAdapter: String
    private lateinit var userIdFromGlobal: String
    private lateinit var adminIdFromGlobal: String
    private lateinit var userNameFromGlobal: String
    private lateinit var roomIdFromGlobal: String
    private lateinit var capacity: String
    private lateinit var userNameList: ArrayList<String>
    private lateinit var participantIdList: ArrayList<String>
    private lateinit var toolbar: MaterialToolbar


    @SuppressLint("SuspiciousIndentation", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userIdFromGlobal = Constants.GlobalData.userID
        adminIdFromGlobal = Constants.GlobalData.admin_id
        userNameFromGlobal = Constants.GlobalData.userNickname
        roomIdFromGlobal = Constants.GlobalData.room_id


        toolbar = findViewById(R.id.toolbar_details_room)

        binding.RoomNameTv.text = intent.extras?.get("newtitle").toString()
        setSupportActionBar(toolbar)
        // supportActionBar?.title = "fgjhkj"
        supportActionBar?.title = intent.extras?.get("newtitle").toString()


        val overflowIcon = toolbar.overflowIcon
        overflowIcon?.mutate()
            ?.setColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_IN)
        toolbar.overflowIcon = overflowIcon

        join_button.setOnClickListener {
            join()
        }

        activityIdFromAdapter = intent.getStringExtra("activity_id").toString()
        if (adminIdFromGlobal == userIdFromGlobal) {
            join_button.visibility = View.GONE
        }
        val time = intent.extras?.get("newdate")



        binding.locationTv.text = intent.extras?.get("newloc").toString()
        val formattedTime = SimpleDateFormat("HH:mm:ss", Locale.US).format(time)

        binding.timeTv.text = "$formattedTime\n${
            SimpleDateFormat("yyyy-MM-dd", Locale.US).format(time)
        }"

        userNameList = ArrayList()

        binding.slogan.text = intent.extras?.get("newslogan").toString()
        binding.desc.text = intent.extras?.get("short_desc").toString()
        /*
        binding.user4.text = intent.extras?.get("newuser").toString()
        binding.user1.text = intent.extras?.get("newuser1").toString()
        binding.user2.text = intent.extras?.get("newuser2").toString()
        binding.user3.text = intent.extras?.get("newuser3").toString()
        binding.desc.text = intent.extras?.get("newdes").toString()
        binding.slogan.text = intent.extras?.get("newslogan").toString()
        */
        userNameList = intent.getStringArrayListExtra("userNames") as ArrayList<String>
        participantIdList = intent.getStringArrayListExtra("userIds") as ArrayList<String>
        capacity = intent.getStringExtra("capacity").toString()
        binding.participantNumberTv.text = capacity

        binding.recyclerViewUsers.layoutManager = LinearLayoutManager(this)

        userListAdapter = userNameList?.let { UserAdapter(it) }!!
        binding.recyclerViewUsers.adapter = userListAdapter
        userListAdapter.notifyDataSetChanged()

        //ll_layout_bar.isVisible = userNameList.contains(userNameFromGlobal)
        val category_id = intent.extras?.get("newimage").toString()

        when (category_id) {
            "639f73052b33750031d145f9" -> binding.realImage.setBackgroundResource(R.drawable.yoga)

            "638e69cd4321c700312ff67b" -> binding.realImage.setBackgroundResource(R.drawable.plates)

            "638e675d4321c700312ff674" -> binding.realImage.setBackgroundResource(R.drawable.cardio)

            "638e69544321c700312ff679" -> binding.realImage.setBackgroundResource(R.drawable.running)


            else -> { // Note the block

            }
        }

        when (category_id) {
            "639f73052b33750031d145f9" -> category_tv.text = "Yoga"

            "638e69cd4321c700312ff67b" -> category_tv.text = "Pilates"

            "638e675d4321c700312ff674" -> category_tv.text = "Cardio"

            "638e69544321c700312ff679" -> category_tv.text = "Running"
            else -> { // Note the block

            }

        }

        button_goto_chat.setOnClickListener {
            val intent = Intent(this@RoomDetailsActivity, ChatActivity::class.java)
            intent.putExtra("activityID", activityIdFromAdapter)
            intent.putExtra("userNameList", userNameList)
            startActivity(intent)
        }


        expandedFab2.setOnClickListener {
            val intent = Intent(this@RoomDetailsActivity, VoteActivity::class.java)
            intent.putExtra("userNameList", userNameList)
            intent.putExtra("userIds", participantIdList)
            intent.putExtra("adminID", userIdFromGlobal)
            startActivity(intent)
        }

        main_fab.setOnClickListener {
            if (button_goto_chat.visibility == View.VISIBLE) {
                button_goto_chat.visibility = View.GONE
                expandedFab2.visibility = View.GONE
            } else {
                button_goto_chat.visibility = View.VISIBLE
                expandedFab2.visibility = View.VISIBLE
            }
        }

    }


    @SuppressLint("NotifyDataSetChanged")
    private fun join() {
        val newUserNameList = ArrayList<String>() // Temporary list for updated usernames

        if (capacity.toInt() <= userNameList.size) {
            Toast.makeText(
                this@RoomDetailsActivity,
                "Room is full capacity, you can not joint",
                Toast.LENGTH_SHORT
            ).show()
        } else if (userNameList.contains(userNameFromGlobal)) {
            Toast.makeText(
                this@RoomDetailsActivity,
                "You already a member of this room.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            expandedFab2.isVisible = false
            val queue = Volley.newRequestQueue(this@RoomDetailsActivity)

            val urlString = "${Constants.BASE_URL}${Constants.END_POINT_JOIN}$activityIdFromAdapter"

            val json =
                JSONObject().apply {
                    put("operation", "update")
                    put("user_id", userIdFromGlobal)
                    put("user_name", userNameFromGlobal)
                }

            val request = object : JsonObjectRequest(Method.PUT, urlString, json,
                { response ->
                    Toast.makeText(
                        this@RoomDetailsActivity,
                        "Joined the activity room",
                        Toast.LENGTH_LONG
                    ).show()

                    val responseJson = JSONObject(response.toString())
                    val contentsJson = responseJson.getJSONObject("contents")
                    val clientsInfosArray = contentsJson.getJSONArray("clients_infos")
                    for (i in 0 until clientsInfosArray.length()) {
                        val clientInfoJson = clientsInfosArray.getJSONObject(i)
                        val userName = clientInfoJson.getString("user_name")
                        Log.d("userName", userName)

                        if (!userNameList.contains(userName)) {
                            newUserNameList.add(userName) // Add to temporary list only if it doesn't exist in userNameList
                        }
                    }
                    userNameList.addAll(newUserNameList) // Add new usernames to the original list
                    recyclerViewUsers.scrollToPosition(userListAdapter.itemCount - 1)

                    userListAdapter.notifyDataSetChanged()


                },
                { error ->
                    // Handle error
                    Log.e("Join", "Error joining room: $error")
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    return mutableMapOf(
                        "Content-Type" to "application/json"
                    )
                }
            }

            queue.add(request)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_details_activity, menu)
        return true
    }


    private fun exit() {
        val newUserNameList = ArrayList<String>() // Temporary list for updated usernames

        if (!userNameList.contains(userNameFromGlobal)) {
            Toast.makeText(
                this@RoomDetailsActivity,
                "You are not a participant of this room",
                Toast.LENGTH_SHORT
            ).show()
        } else {

            val queue = Volley.newRequestQueue(this@RoomDetailsActivity)

            val urlString =
                "${Constants.BASE_URL}${Constants.END_POINT_JOIN}$activityIdFromAdapter"

            val json =
                JSONObject().apply {
                    put("operation", "delete")
                    put("user_id", userIdFromGlobal)
                    put("user_name", userNameFromGlobal)
                }

            val request = object : JsonObjectRequest(Method.PUT, urlString, json,
                { response ->
                    val responseJson = JSONObject(response.toString())
                    val contentsJson = responseJson.getJSONObject("contents")
                    val clientsInfosArray = contentsJson.getJSONArray("clients_infos")
                    for (i in 0 until clientsInfosArray.length()) {
                        val clientInfoJson = clientsInfosArray.getJSONObject(i)
                        val userName = clientInfoJson.getString("user_name")
                        Log.d("userName", userName)
                        // userNameList.add(userName)
                        recyclerViewUsers.scrollToPosition(userListAdapter.itemCount - 1)
                        userListAdapter.notifyDataSetChanged()


                    }
                },
                { error ->
                    // Handle error
                    Log.e("Join", "Error joining room: $error")
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    return mutableMapOf(
                        "Content-Type" to "application/json"
                    )
                }
            }
            queue.add(request)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exit_room -> {
                exit()

                val intent = Intent(this@RoomDetailsActivity,RoomListActivity::class.java)
                startActivity(intent)


                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }


}