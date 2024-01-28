package com.example.sportbud3.services

import com.example.sportbud.Model.RoomListItem
import com.example.sportbud3.Constants.END_POINT_GET
import com.example.sportbud3.Constants.END_POINT_JOIN
import com.example.sportbud3.Constants.END_POINT_POST
import com.example.sportbud3.Constants.END_POINT_PROFILE
import com.example.sportbud3.Model.UserItem
import com.example.sportbud3.Model.UserResponceItem
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface PostApi {
    /*
    @GET(END_POINT_GET)
    suspend fun getAllPosts(): Response<List<WelcomeElement>>
*/
    @GET(END_POINT_GET)
    suspend fun getAllRooms(): Response<List<RoomListItem>>

    @GET(END_POINT_GET)
    suspend fun getAOneRooms(): Response<RoomListItem>

    @POST(END_POINT_POST)
    suspend fun posting(
        @Body postItem: RoomListItem
    ): Call<RoomListItem>

    @GET(END_POINT_PROFILE)
    suspend fun getAllCredentials(): Response<List<UserResponceItem>>

    @PUT
    suspend fun createEmployee(@Url url: String, @Body requestBody: RequestBody): Response<RoomListItem>


    @GET("users/{_id}")
    suspend fun getUserData(@Path("_id") id: String): UserItem
}

data class RequestBody(
    val operation: String,
    val user_id: String,
    val user_name: String
)