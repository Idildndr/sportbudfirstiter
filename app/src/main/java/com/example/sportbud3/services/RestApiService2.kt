package com.example.sportbud3.services

import android.content.ContentValues.TAG
import android.util.Log
import com.example.sportbud.Model.RoomListItem
import com.example.sportbud3.Model.Contents
import com.example.sportbud3.Model.UserItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService2 {
    fun addRoom(roomListItem: RoomListItem, onResult: (RoomListItem) -> Unit) {
        val retrofit = ServiceBuilder2.buildService(RestApi2::class.java)
        retrofit.addRoom(roomListItem).enqueue(
            object : Callback<RoomListItem> {
                override fun onFailure(call: Call<RoomListItem>, t: Throwable) {
                    Log.d("nil", "eroor")
                }

                override fun onResponse(
                    call: Call<RoomListItem>,
                    response: Response<RoomListItem>
                ) {
                    val addRoom = response.body()
                    if (addRoom != null) {
                        onResult(addRoom)
                    }
                }
            }
        )
    }

    fun addUser(user: UserItem, onResult: (UserItem) -> Unit) {
        val retrofit = ServiceBuilder2.buildService(RestApi2::class.java)
        retrofit.addUserLogin(user).enqueue(
            object : Callback<UserItem> {
                override fun onFailure(call: Call<UserItem>, t: Throwable) {
                    Log.d("idil", "eroor")
                }

                override fun onResponse(
                    call: Call<UserItem>,
                    response: Response<UserItem>
                ) {
                    val addUser = response.body()
                    if (addUser != null) {
                        onResult(addUser)
                    }

                }
            }
        )
    }

    /*
        fun getUserCredentials1(){
            val retrofit = ServiceBuilder2.buildService(RestApi2::class.java)
            val call = retrofit.getUserCredentials()
            call.enqueue(object :Callback<Contents>{
                override fun onResponse(call: Call<Contents>, response: Response<Contents>) {
                    if(response.isSuccessful){
                        val email=  response.body()?.email
                        val password = response.body()?.password
                        Log.d(TAG, "Email: $email, Password: $password")
                    }
                    else
                    {

                    }
                }

                override fun onFailure(call: Call<Contents>, t: Throwable) {
                    Log.d(TAG, "Çilek")
                }
            })
        }
        */
    fun getUserCredentials() {
        val retrofit = ServiceBuilder2.buildService(RestApi2::class.java)
        val call = retrofit.getUserCredentials()
        if (call.isSuccessful) {
            val email = call.body()?.get(0)?.contents?.email
            Log.d(TAG, "Email: $email")

        } else {

            Log.d(TAG, "Çilek")

        }
    }


    fun getAllRooms2() {
        val retrofit = ServiceBuilder2.buildService(RestApi2::class.java)
        val call = retrofit.getAllRooms()


    }


}