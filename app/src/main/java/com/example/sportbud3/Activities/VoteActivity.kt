package com.example.sportbud3.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportbud3.Adapters.VoteAdapter
import com.example.sportbud3.Constants
import com.example.sportbud3.R
import com.example.sportbud3.services.CustomItemDecoration
import kotlinx.android.synthetic.main.activity_create_room.*
import kotlinx.android.synthetic.main.activity_room_details.*
import kotlinx.android.synthetic.main.vote.*
import kotlinx.android.synthetic.main.vote_item.*

class VoteActivity : AppCompatActivity() {


    private lateinit var loyalityScore: String
    private lateinit var fairPlayScore: String
    private lateinit var performanceyScore: String
    private lateinit var roomIdFromGlobal: String
    private lateinit var userIdFromGlobal: String
    private lateinit var adminIdFromGlobal: String
    private lateinit var userNameFromGlobal: String
    private lateinit var userNameList: ArrayList<String>
    private lateinit var participantIdList: ArrayList<String>
    private var userID = Constants.GlobalData.userID
    private var usernickname = Constants.GlobalData.userNickname
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vote)

        roomIdFromGlobal = Constants.GlobalData.room_id
        userIdFromGlobal = Constants.GlobalData.userID
        adminIdFromGlobal = Constants.GlobalData.admin_id
        userNameFromGlobal = Constants.GlobalData.userNickname

        userNameList = intent.getStringArrayListExtra("userNameList") as ArrayList<String>
        participantIdList = intent.getStringArrayListExtra("userIds") as ArrayList<String>


        val adapter = VoteAdapter(userNameList, participantIdList, this@VoteActivity)
        //  adapter.setMyString(Constants.GlobalData.activityID)
        recyclerViewScoresView.adapter = adapter
        recyclerViewScoresView.layoutManager = LinearLayoutManager(this@VoteActivity)


        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        val itemDecoration = CustomItemDecoration(spacingInPixels)

        recyclerViewScoresView.addItemDecoration(itemDecoration)


    }
}