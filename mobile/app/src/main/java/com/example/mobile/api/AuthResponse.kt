package com.example.mobile.api

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("token") val token: String,
    @SerializedName("type") val type: String? = "Bearer",
    @SerializedName("user") val user: UserResponse
)
