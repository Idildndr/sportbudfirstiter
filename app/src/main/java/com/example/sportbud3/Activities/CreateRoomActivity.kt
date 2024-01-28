package com.example.sportbud3.Activities

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.sportbud.Model.Contents1
import com.example.sportbud.Model.RoomListItem
import com.example.sportbud3.Constants
import com.example.sportbud3.R
import com.example.sportbud3.services.RestApiService2
import kotlinx.android.synthetic.main.activity_create_room.*
import kotlinx.android.synthetic.main.activity_room_details.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class CreateRoomActivity : AppCompatActivity() {
    private lateinit var selectedDate: Date
    private lateinit var selectedTime: Date
    private lateinit var lat: String
    private lateinit var long: String
    private lateinit var placeName: String
    private lateinit var partNumber: String
    private lateinit var selectedInt: String
    private lateinit var RoomIdToBeCreated: String
    private lateinit var ISO_DATE: Date
    var formatDate = SimpleDateFormat("dd MMMM ", Locale.US)
    var formatTime = SimpleDateFormat("HH:MM ", Locale.US)
    private val PERMISSION_CODE = 1001;

    private val IMAGE_PICK_CODE = 202
    private var Date = ""
    private var dateAndTime = ""
    private var roomName = ""
    private var roomDesc = ""
    private var roomCategory = ""
    private var roomPartNumber = ""
    private var userID = Constants.GlobalData.userID
    private var usernickname = Constants.GlobalData.userNickname

    val arrayList = ArrayList<RoomListItem>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_room)

        lat = intent.extras?.get("lat").toString()
        long = intent.extras?.get("long").toString()
        placeName = intent.extras?.get("placeName").toString()

        val categories = resources.getStringArray(R.array.Categories)
        val arrayAdapterCategories =
            ArrayAdapter(this, R.layout.categories_dropdown_item, categories)
        val autoComplete = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView_Categories)
        autoComplete.setAdapter(arrayAdapterCategories)


        val participantNum = resources.getStringArray(R.array.Participants)
        val arrayAdapterPartNum =
            ArrayAdapter(this, R.layout.categories_dropdown_item, participantNum)
        val autoCompletePartNum =
            findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView_ParticipantNumber)
        autoCompletePartNum.setAdapter(arrayAdapterPartNum)


        val catMap = mapOf(
            "Yoga" to "639f73052b33750031d145f9",
            "Plates" to "638e69cd4321c700312ff67b",
            "Cardio" to "638e675d4321c700312ff674",
            "Running" to "638e69544321c700312ff679"
        )




        autoComplete.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Log.d("SELECTED ITEM", selectedItem)
            RoomCategoryTxtSet.text = selectedItem.toString()
            selectedInt = catMap[selectedItem].toString()
        }

        autoCompletePartNum.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Log.d("SELECTED ITEM", selectedItem)
            partNumber = selectedItem
            RoomNumberTxtSet.text = selectedItem.toString()
        }



        go_to_maps.setOnClickListener {
            val intent = Intent(this, MyActivity::class.java)
            startActivity(intent)

        }




        datePickerButton.setOnClickListener {
            val currentDate = Calendar.getInstance()
            val year = currentDate.get(Calendar.YEAR)
            val month = currentDate.get(Calendar.MONTH)
            val day = currentDate.get(Calendar.DAY_OF_MONTH)
            val datePicker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedDateCalendar = Calendar.getInstance().apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    }
                    selectedDate = selectedDateCalendar.time
                    val formattedDate =
                        SimpleDateFormat("yyyy-MM-dd", Locale.US).format(selectedDate)
                    RoomTimeTxtSet.text = formattedDate
                    date_Text.text = formattedDate
                },
                year, month, day
            )
            datePicker.show()

        }

        timePickerButton.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val hour = currentTime.get(Calendar.HOUR_OF_DAY)
            val minute = currentTime.get(Calendar.MINUTE)
            val timePicker = TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    val selectedTimeCalendar = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, hourOfDay)
                        set(Calendar.MINUTE, minute)
                    }
                    selectedTime = selectedTimeCalendar.time
                    val formattedTime = SimpleDateFormat("HH:mm:ss", Locale.US).format(selectedTime)
                    RoomTimeTxtSet.text = "$formattedTime\n${
                        SimpleDateFormat("yyyy-MM-dd", Locale.US).format(selectedDate)
                    }"
                    val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
                        timeZone = TimeZone.getTimeZone("UTC")
                    }
                    val isoDateTime = isoFormat.format(Date(selectedDate.time))
                    Log.d("ISO DateTime", isoDateTime)
                    ISO_DATE = Date()
                    ISO_DATE = Date(selectedDate.time)
                    time_text.text = formattedTime.toString()

                },
                hour, minute, true
            )
            timePicker.show()
        }

        editTextRoomName.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                RoomNameTxtSet.setText(s)
            }
        })

        val dp = datePickerButton
        val tp = timePickerButton
        RoomLocTxtSet.text = placeName
        RoomCategoryTxtSet.text = autoCompleteTextView_Categories.text
        autoCompleteTextView_ParticipantNumber.text = autoCompletePartNum.text
        RoomNameTxtSet.text = editTextRoomName.text
        roomName = RoomNameTxtSet.text.toString()
        button_submit_room.setOnClickListener {

            Log.i("date", Date)

            Log.i("idil", autoCompleteTextView_Categories.text.toString())
            createRoom()
            val intent = Intent(this@CreateRoomActivity, MainActivity::class.java)
            startActivity(intent)

        }

    }


    private fun createRoom() {
        val category = ""
        RoomCategoryTxtSet.text = category
        var cetgoryID = ""
        val catMap = mapOf(
            "Yoga" to "639f73052b33750031d145f9",
            "Plates" to "638e69cd4321c700312ff67b",
            "Cardio" to "638e675d4321c700312ff674",
            "Running" to "638e69544321c700312ff679"
        )

        roomName = editTextRoomName.text.toString()
        roomPartNumber = RoomNumberTxtSet.text.toString()



        roomDesc = editTextRoomDesc.text.toString()


        val apiService = RestApiService2()

        val roomInfo = RoomListItem(
            null,
            Contents1(
                usernickname,
                userID,
                partNumber.toString().toInt(),
                selectedInt,
                null,
                roomDesc,
                0,
                null,
                lat,
                placeName,
                long, 10,
                6,
                "will get rid of your muscle pain and you will feel more vigorous.",
                "Do Pilates and stay young forever.",
                ISO_DATE
            ),
            ISO_DATE, 0, "en", "", 0, true, roomName
        )

        apiService.addRoom(roomInfo) {
            if (it != null) {
                RoomIdToBeCreated = it._id.toString()
                Log.d("roomlog", roomInfo.toString())
                addAdmin()
                Toast.makeText(
                    this@CreateRoomActivity,
                    "Room is successfully created",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Log.d("ruj", "Error registering new user")
            }
        }

    }


    fun addAdmin() {
        val queue = Volley.newRequestQueue(this@CreateRoomActivity)

        val urlString = "${Constants.BASE_URL}${Constants.END_POINT_JOIN}$RoomIdToBeCreated"

        val json =
            JSONObject().apply {
                put("operation", "update")
                put("user_id", userID)
                put("user_name", usernickname)
            }

        val request = object : JsonObjectRequest(
            Method.PUT, urlString, json,
            { response ->
                Toast.makeText(
                    this@CreateRoomActivity,
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

                }

            },
            { error ->
                // Handle error
                Log.e("Join", "Error admin room: $error")
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
