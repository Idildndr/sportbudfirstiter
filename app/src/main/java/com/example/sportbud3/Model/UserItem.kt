package com.example.sportbud3.Model

import com.google.gson.annotations.SerializedName

data class UserItem(
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

data class Contents(
    val about: String,
    val email: String,
    val fair_play: List<FairPlay>?,
    val favorite_branches: List<String>?,
    val firstname: String,
    val lastname: String,
    val location: String,
    val loyalty: List<Loyalty>?,
    val nickname: String,
    val password: String,
    val performance: List<Performance>?
)
data class FairPlay(
    val id: String,
    val score: Int
)

data class Performance(
    val id: String,
    val score: Int
)
data class Loyalty(
    val id: String,
    val score: Int
)