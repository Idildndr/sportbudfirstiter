package com.example.sportbud3.Model

import com.google.gson.annotations.SerializedName

data class LoyaltyData(
    @SerializedName("user_id") val userId: String,
    @SerializedName("activity_id") val activityId: String,
    @SerializedName("score") val score: Int
)
