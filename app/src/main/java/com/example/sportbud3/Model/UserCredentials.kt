package com.example.sportbud.Model

import com.google.gson.annotations.SerializedName

data class UserCredentials(
        @SerializedName("email") val email: String,
        @SerializedName("password") val password: String
    )

