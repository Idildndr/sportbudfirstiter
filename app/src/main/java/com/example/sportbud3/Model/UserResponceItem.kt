package com.example.sportbud3.Model

data class UserResponceItem(
    val _id: String,
    val contents: Contents,
    val date: String,
    val deleted: Int,
    val google_oauth2_id: String,
    val lang: String,
    val parent_id: String,
    val sort: Int,
    val status: Boolean,
    val title: String
)