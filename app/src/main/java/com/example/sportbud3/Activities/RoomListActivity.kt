package com.example.sportbud3.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportbud.Model.RoomListItem
import com.example.sportbud3.Adapters.RoomListItemAdapter
import com.example.sportbud3.databinding.ActivityRoomListBinding
import com.example.sportbud3.services.RetrofitInstance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_room_list.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class RoomListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoomListBinding
    private lateinit var roomListAdapter: RoomListItemAdapter
    private lateinit var cardioList: ArrayList<RoomListItem>
    private lateinit var yogaList: ArrayList<RoomListItem>
    private lateinit var platesList: ArrayList<RoomListItem>
    private lateinit var runningList: ArrayList<RoomListItem>
    private lateinit var allRoomsList: ArrayList<RoomListItem>
    private lateinit var dateroom: ArrayList<RoomListItem>
    private lateinit var allRoomsListYoga: ArrayList<RoomListItem>
    private lateinit var allRoomsListCardio: ArrayList<RoomListItem>
    private lateinit var allRoomsListPlates: ArrayList<RoomListItem>
    private lateinit var allRoomsListRunning: ArrayList<RoomListItem>
    private var isCardioPressed = false
    private var isYogaPressed = false
    private var isAllPressed = false
    private var isPlatesPressed = false
    private var isRunningPressed = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recylerviewCardio.visibility == View.GONE
        recylerviewYoga.visibility == View.VISIBLE


        // set up initial recycler view

        // get all rooms
        getAllRooms()

        setUpRV()
        setUpYogaRV()
        setUpPlatesRV()
        setUpRunningRV()
        setUpCardioRV()

        binding.yogaBUtton.setOnClickListener {
            toggleRecyclerViewVisibility(binding.recylerviewCardio)
            toggleRecyclerViewVisibility(binding.recylerviewYoga)
            toggleRecyclerViewVisibility(binding.recylerviewRunning)
            toggleRecyclerViewVisibility(binding.recylerviewPlates)
            toggleRecyclerViewVisibility(binding.recylerviewAll)
            recylerviewYoga.visibility = View.GONE
            getYoga()
        }
        binding.cardioButton.setOnClickListener {
            toggleRecyclerViewVisibility(binding.recylerviewCardio)
            toggleRecyclerViewVisibility(binding.recylerviewYoga)
            toggleRecyclerViewVisibility(binding.recylerviewRunning)
            toggleRecyclerViewVisibility(binding.recylerviewPlates)
            toggleRecyclerViewVisibility(binding.recylerviewAll)
            getCardio()
        }

        binding.allButton.setOnClickListener {
            toggleRecyclerViewVisibility(binding.recylerviewCardio)
            toggleRecyclerViewVisibility(binding.recylerviewYoga)
            toggleRecyclerViewVisibility(binding.recylerviewRunning)
            toggleRecyclerViewVisibility(binding.recylerviewPlates)
            toggleRecyclerViewVisibility(binding.recylerviewAll)
            toggleRecyclerViewVisibility(binding.recylerviewAll)
            recylerviewAll.visibility = View.GONE
            getAllRooms()
        }
        binding.runningButton.setOnClickListener {
            toggleRecyclerViewVisibility(binding.recylerviewCardio)
            toggleRecyclerViewVisibility(binding.recylerviewYoga)
            toggleRecyclerViewVisibility(binding.recylerviewRunning)
            toggleRecyclerViewVisibility(binding.recylerviewPlates)
            toggleRecyclerViewVisibility(binding.recylerviewAll)
            getRunning()
        }

        binding.platesButton.setOnClickListener {
            toggleRecyclerViewVisibility(binding.recylerviewCardio)
            toggleRecyclerViewVisibility(binding.recylerviewYoga)
            toggleRecyclerViewVisibility(binding.recylerviewRunning)
            toggleRecyclerViewVisibility(binding.recylerviewPlates)
            toggleRecyclerViewVisibility(binding.recylerviewAll)
            getPlates()
        }
        // Function to toggle the visibility of a RecyclerView


        floatingActionButton2.setOnClickListener {
            val intent = Intent(this@RoomListActivity, CreateRoomActivity::class.java)
            startActivity(intent)
        }


    }

    fun toggleRecyclerViewVisibility(recyclerView: RecyclerView) {
        if (recyclerView.visibility == View.VISIBLE) {
            recyclerView.visibility = View.GONE
        } else {
            recyclerView.visibility = View.VISIBLE
        }
    }

    fun compareDates(date1: Date, date2: Date): Int {
        return date1.compareTo(date2)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDateTime(): Date {
        val currentDateTime = OffsetDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val formattedDateTime = currentDateTime.format(formatter)
        return Date.from(
            LocalDateTime.parse(formattedDateTime, formatter).toInstant(OffsetDateTime.now().offset)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAllRooms() {

        lifecycleScope.launchWhenCreated {
            dateroom = ArrayList()

            val response = RetrofitInstance.retrofit.getAllRooms()
            if (response.isSuccessful && response.body() != null) {
                dateroom = response.body()!!.toList() as ArrayList<RoomListItem>
                dateroom.toMutableList()
                val iterator = dateroom.iterator()
                while (iterator.hasNext()) {
                    val item = iterator.next()
                    if (item.contents?.startedActRoom?.compareTo(getCurrentDateTime())!! < 0) {
                        iterator.remove()
                    }
                }

                roomListAdapter.differ.submitList(dateroom)


//                Log.d("response", "getAllPosts: ${response.body()}")
            } else {
                Toast.makeText(
                    this@RoomListActivity,
                    "Error Code: ${response.code()} ",
                    Toast.LENGTH_LONG
                ).show()

            }

        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getPlates() {
        lifecycleScope.launchWhenCreated {
            platesList = ArrayList()
            val response = RetrofitInstance.retrofit.getAllRooms()
            if (response.isSuccessful && response.body() != null) {
                allRoomsListPlates = response.body()!!.toList() as ArrayList<RoomListItem>
                platesList.toMutableList()
                for (item in allRoomsListPlates.indices) {
                    val iterator = platesList.iterator()
                    while (iterator.hasNext()) {
                        val item = iterator.next()
                        if (item.contents?.startedActRoom?.compareTo(getCurrentDateTime())!! < 0) {
                            iterator.remove()
                        }
                    }

                    if (allRoomsListPlates[item].contents?.category_id == "638e69cd4321c700312ff67b") {
                        platesList.add(allRoomsListPlates[item])




                        roomListAdapter.differ.submitList(platesList)

                        Log.d("olsun", "getPlates: ${allRoomsListPlates[item].title}")

                    }
                }

                //   Log.d("olsun", "getCardio: ${cardioList.toString()}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getRunning() {
        lifecycleScope.launchWhenCreated {
            runningList = ArrayList()
            val response = RetrofitInstance.retrofit.getAllRooms()
            if (response.isSuccessful && response.body() != null) {
                allRoomsListRunning = response.body()!!.toList() as ArrayList<RoomListItem>
                runningList.toMutableList()
                for (item in allRoomsListRunning.indices) {
                    val iterator = runningList.iterator()
                    while (iterator.hasNext()) {
                        val item = iterator.next()
                        if (item.contents?.startedActRoom?.compareTo(getCurrentDateTime())!! < 0) {
                            iterator.remove()
                        }
                    }



                    if (allRoomsListRunning[item].contents?.category_id == "638e69544321c700312ff679") {
                        runningList.add(allRoomsListRunning[item])
                        //  roomListAdapterRunning.differ.submitList(runningList)
                        roomListAdapter.differ.submitList(runningList)

                    }
                }

                //   Log.d("olsun", "getCardio: ${cardioList.toString()}")
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCardio() {
        lifecycleScope.launchWhenCreated {
            cardioList = ArrayList()
            val response = RetrofitInstance.retrofit.getAllRooms()
            if (response.isSuccessful && response.body() != null) {
                allRoomsListCardio = response.body()!!.toList() as ArrayList<RoomListItem>
                cardioList.toMutableList()
                for (item in allRoomsListCardio.indices) {

                    val iterator = cardioList.iterator()
                    while (iterator.hasNext()) {
                        val item = iterator.next()
                        if (item.contents?.startedActRoom?.compareTo(getCurrentDateTime())!! < 0) {
                            iterator.remove()
                        }
                    }


                    if (allRoomsListCardio[item].contents?.category_id == "638e675d4321c700312ff674") {
                        cardioList.add(allRoomsListCardio[item])
                        roomListAdapter.differ.submitList(cardioList)
                    }
                }

                //   Log.d("olsun", "getCardio: ${cardioList.toString()}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getYoga() {
        lifecycleScope.launchWhenCreated {
            yogaList = ArrayList()
            val response = RetrofitInstance.retrofit.getAllRooms()
            if (response.isSuccessful && response.body() != null) {
                allRoomsListYoga = response.body()!!.toList() as ArrayList<RoomListItem>
                yogaList.toMutableList()
                for (item in allRoomsListYoga.indices) {
                    val iterator = yogaList.iterator()
                    while (iterator.hasNext()) {
                        val item = iterator.next()
                        if (item.contents?.startedActRoom?.compareTo(getCurrentDateTime())!! < 0) {
                            iterator.remove()
                        }
                    }

                    if (allRoomsListYoga[item].contents?.category_id == "639f73052b33750031d145f9") {
                        yogaList.add(allRoomsListYoga[item])
                        roomListAdapter.differ.submitList(yogaList)


                    }
                }

                //   Log.d("olsun", "getCardio: ${cardioList.toString()}")
            }
        }
    }


    private fun setUpCardioRV() {
        roomListAdapter = RoomListItemAdapter()
        binding.recylerviewCardio.apply {
            layoutManager = GridLayoutManager(this@RoomListActivity, 2)
            adapter = roomListAdapter
        }
    }


    private fun setUpRunningRV() {
        roomListAdapter = RoomListItemAdapter()
        binding.recylerviewCardio.apply {
            layoutManager = GridLayoutManager(this@RoomListActivity, 2)
            adapter = roomListAdapter
        }
    }

    private fun setUpPlatesRV() {
        roomListAdapter = RoomListItemAdapter()
        binding.recylerviewCardio.apply {
            layoutManager = GridLayoutManager(this@RoomListActivity, 2)
            adapter = roomListAdapter
        }
    }


    private fun setUpYogaRV() {
        roomListAdapter = RoomListItemAdapter()
        binding.recylerviewCardio.apply {
            layoutManager = GridLayoutManager(this@RoomListActivity, 2)
            adapter = roomListAdapter
        }
    }

    private fun setUpRV() {
        roomListAdapter = RoomListItemAdapter()
        binding.recylerviewAll.apply {
            layoutManager = GridLayoutManager(this@RoomListActivity, 2)
            adapter = roomListAdapter
        }
    }


}
