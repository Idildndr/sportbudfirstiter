package com.example.sportbud.Model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class RoomListItem(
    val _id: String?,
    @SerializedName("contents")
    val contents:Contents1?,
    val date: Date?,
    val deleted: Int?,
    val lang: String?,
    val parent_id: String?,
    val sort: Int?,
    val status: Boolean?,
    var title: String
)

data class Clientsİnfo(
    val registeredAt: String?,
    val user_id: String?,
    val user_name:String?
)

data class Contents1(
    val admin: String?,
    val admin_id: String?,
    val capacity: Int?,
    val category_id: String?,
    val clients_infos: List<Clientsİnfo>?,
    val description: String?,
    val duration: Int?,
    val finishedActRoom: Date?,
    val latitude: String?,
    val location: String?,
    val longitude: String?,
    val max_client: Int?,
    val min_client: Int?,
    val short_description: String?,
    val slogan: String?,
    val startedActRoom: Date?
)