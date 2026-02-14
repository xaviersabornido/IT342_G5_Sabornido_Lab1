package com.example.mobile.api

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("username") val username: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("createdAt") val createdAt: String? = null,
    @SerializedName("roles") val roles: List<String>? = null
)
