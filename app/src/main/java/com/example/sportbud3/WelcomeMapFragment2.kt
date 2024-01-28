package com.example.sportbud3

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportbud.Model.RoomListItem
import com.example.sportbud3.Activities.RoomDetailsActivity
import com.example.sportbud3.Adapters.RoomListItemAdapter
import com.example.sportbud3.services.RetrofitInstance
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_welcome_map.*
import kotlinx.android.synthetic.main.fragment_welcome_map2.*
import kotlinx.android.synthetic.main.fragment_welcome_map2.view.*


class WelcomeMapFragment2 : Fragment() {
    private lateinit var roomListAdapter: RoomListItemAdapter

    private var mMapView: MapView? = null

    private lateinit var RoomListArray: ArrayList<RoomListItem>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_welcome_map2, container, false)
        getAllRooms()



        mMapView = rootView.mapView22
        mMapView?.onCreate(savedInstanceState)

        mMapView?.getMapAsync(object : OnMapReadyCallback {
            override fun onMapReady(googleMap: GoogleMap) {

                googleMap.uiSettings.isZoomControlsEnabled = true

                lifecycleScope.launchWhenCreated {
                    val response = RetrofitInstance.retrofit.getAllRooms()
                    if (response.isSuccessful && response.body() != null) {


                        val bitmapYoga =
                            BitmapFactory.decodeResource(resources, R.drawable.yoga_pin)
                        val bitmapPlates =
                            BitmapFactory.decodeResource(resources, R.drawable.plates_pin)
                        val bitmapCardio =
                            BitmapFactory.decodeResource(resources, R.drawable.cardio_pin)
                        val bitmapRunning =
                            BitmapFactory.decodeResource(resources, R.drawable.running_pin)
                        val resizedBitmapYoga =
                            Bitmap.createScaledBitmap(bitmapYoga, 150, 150, false)
                        val resizedBitmapPlates =
                            Bitmap.createScaledBitmap(bitmapPlates, 150, 150, false)
                        val resizedBitmapCardio =
                            Bitmap.createScaledBitmap(bitmapCardio, 150, 150, false)
                        val resizedBitmapRunning =
                            Bitmap.createScaledBitmap(bitmapRunning, 150, 150, false)

                        val finalYoga = BitmapDescriptorFactory.fromBitmap(
                            resizedBitmapYoga
                        )

                        val finalPlates = BitmapDescriptorFactory.fromBitmap(
                            resizedBitmapPlates
                        )
                        val finalCardio = BitmapDescriptorFactory.fromBitmap(
                            resizedBitmapCardio
                        )

                        val finalRunning = BitmapDescriptorFactory.fromBitmap(
                            resizedBitmapRunning
                        )

                        //


                        RoomListArray = response.body()!!.toMutableList() as ArrayList<RoomListItem>
                        RoomListArray.forEach {
                            val temp = it.contents?.latitude?.let { it1 ->
                                it.contents.longitude?.let { it2 ->

                                        LatLng(it1.toDouble(), it2.toDouble())

                                }
                            }
                            val lat = 38.454991
                            val long = 27.145735
                            val zoomLevel = 11f
                            val izmir = LatLng(lat, long)
                            googleMap.animateCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    izmir,
                                    zoomLevel
                                )
                            )
                            val pinMap = mapOf(
                                "639f73052b33750031d145f9" to finalYoga,
                                "638e69cd4321c700312ff67b" to finalPlates,
                                "638e675d4321c700312ff674" to finalCardio,
                                "638e69544321c700312ff679" to finalRunning
                            )


                            val categoryId = it.contents?.category_id.toString()
                            val pinDrawableId =
                                pinMap[categoryId] ?: R.drawable.ic_baseline_location_on_24


                            val icon = pinDrawableId
                            Log.d("niil", icon.toString())


                            val markerOptions = temp?.let { it1 ->
                                MarkerOptions()
                                    .position(it1)
                                    .title(it.title)
                                    .snippet(it.contents?.slogan)
                                    .icon(
                                        icon as? BitmapDescriptor
                                            ?: BitmapDescriptorFactory.defaultMarker()
                                    )
                            }

                            if (markerOptions != null) {
                                val roomMarker = googleMap.addMarker(markerOptions)
                                if (roomMarker != null) {
                                    roomMarker.tag = it
                                } // attach the RoomListItem object to the marker


                                googleMap.setOnInfoWindowLongClickListener { marker ->
                                    val clickedRoom =
                                        marker.tag as? RoomListItem // retrieve the attached RoomListItem object

                                    if (clickedRoom != null) {
                                        val intent = Intent(
                                            requireActivity(),
                                            RoomDetailsActivity::class.java
                                        )
                                        intent.putExtra("newtitle", clickedRoom.title)

                                        intent.putExtra("newloc", clickedRoom.contents?.location)

                                        intent.putExtra("newdate", clickedRoom.date)

                                        intent.putExtra("newimage", clickedRoom.contents?.category_id)

                                        intent.putExtra("newdes", clickedRoom.contents?.description)


                                        intent.putExtra("newslogan", clickedRoom.contents?.slogan)

                                        intent.putExtra("activity_id", clickedRoom._id)
                                        Constants.GlobalData.admin_id = clickedRoom.contents?.admin_id.toString()
                                        Constants.GlobalData.admin_id = clickedRoom._id.toString()
                                        intent.putExtra("short_desc", clickedRoom.contents?.short_description)


                                        val clientsInfoList = clickedRoom.contents?.clients_infos
                                        val userNameList = clientsInfoList?.map { it.user_name } // extract user_name values from clientsInfoList
                                        intent.putStringArrayListExtra("userNames", ArrayList(userNameList))
                                        startActivity(intent)


                                    }
                                }


                            }
                        }


                    }
                }


            }

        })

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roomListAdapter = RoomListItemAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_maps_fragment2)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = roomListAdapter
    }


    override fun onResume() {
        super.onResume()
        mMapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView?.onLowMemory()
    }

    private fun getAllRooms() {
        lifecycleScope.launchWhenCreated {
            val response = RetrofitInstance.retrofit.getAllRooms()
            if (response.isSuccessful && response.body() != null) {
                Log.d("response", "getAllPosts: ${response.body()}")
            } else {
                Toast.makeText(
                    context,
                    "Error Code: ${response.code()} ",
                    Toast.LENGTH_LONG
                ).show()

            }
            roomListAdapter.differ.submitList(response.body())

        }
    }
}