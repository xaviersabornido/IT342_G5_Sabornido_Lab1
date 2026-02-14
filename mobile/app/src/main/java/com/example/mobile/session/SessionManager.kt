package com.example.mobile.session

import android.content.Context
import android.content.SharedPreferences
import com.example.mobile.api.UserResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SessionManager {

    private const val PREFS_NAME = "mobile_auth"
    private const val KEY_TOKEN = "token"
    private const val KEY_USER = "user"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String?) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun saveUser(user: UserResponse?) {
        if (user == null) {
            prefs.edit().remove(KEY_USER).apply()
        } else {
            prefs.edit().putString(KEY_USER, Gson().toJson(user)).apply()
        }
    }

    fun getUser(): UserResponse? {
        val json = prefs.getString(KEY_USER, null) ?: return null
        return try {
            Gson().fromJson(json, object : TypeToken<UserResponse>() {}.type)
        } catch (_: Exception) {
            null
        }
    }

    fun isLoggedIn(): Boolean = !getToken().isNullOrEmpty()

    fun logout() {
        prefs.edit().clear().apply()
    }
}
