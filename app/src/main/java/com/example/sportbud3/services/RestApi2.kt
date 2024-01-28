package com.example.sportbud3.services

import com.example.sportbud.Model.RoomListItem
import com.example.sportbud.Model.UserCredentials
import com.example.sportbud3.Constants.END_POINT_GET
import com.example.sportbud3.Constants.END_POINT_POST
import com.example.sportbud3.Constants.END_POINT_PROFILE
import com.example.sportbud3.Model.Contents
import com.example.sportbud3.Model.UserItem

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RestApi2 {
    @POST(END_POINT_POST)
     fun addRoom(
        @Body postItem: RoomListItem
    ): Call<RoomListItem>


    @POST(END_POINT_PROFILE)
    fun addUserLogin(
        @Body userItem: UserItem
    ): Call<UserItem>

    @GET(END_POINT_PROFILE)
    fun getUserCredentials(): Response<List<UserItem>>

    @GET(END_POINT_GET)
     fun getAllRooms(): Response<List<RoomListItem>>


}