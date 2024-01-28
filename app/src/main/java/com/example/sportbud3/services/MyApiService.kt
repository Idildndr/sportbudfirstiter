package com.example.sportbud3.services

import com.example.sportbud.Model.Contents1
import com.example.sportbud.Model.RoomListItem
import com.example.sportbud3.Constants
import com.example.sportbud3.Model.LoyaltyData
import com.example.sportbud3.Model.UserResponceItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface MyApiService {
    @GET(Constants.END_POINT_GET)
suspend fun getRoomsByCategory(@Query("category_id")
                                   categoryId: String): List<RoomListItem>

    @GET(Constants.END_POINT_GET)
    suspend fun getRooms(): List<RoomListItem>

    @GET("users/{_id}")
    suspend fun getUserData(@Path("_id") id: String): UserResponceItem


        @PUT("loyalty")
        fun updateLoyalty(@Body loyaltyData: LoyaltyData): Call<ResponseBody>

}