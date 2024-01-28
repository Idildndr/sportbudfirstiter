package com.example.sportbud3.Model

data class Room2(
    val _id: String,
    val contents: Contents,
    val date: String,
    val deleted: Int,
    val lang: String,
    val parent_id: String,
    val sort: Int,
    val status: Boolean,
    val title: String
)

data class Clientsİnfo(
    val registeredAt: String,
    val user_id: String,
    val user_name: String
)


data class Contents3(
    val admin: String,
    val admin_id: String,
    val capacity: Int,
    val category_id: String,
    val clients_infos: List<Clientsİnfo>,
    val description: String,
    val duration: Int,
    val finishedActRoom: String,
    val latitude: String,
    val location: String,
    val longitude: String,
    val max_client: Int,
    val min_client: Int,
    val short_description: String,
    val slogan: String,
    val startedActRoom: String
)