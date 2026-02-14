package com.example.mobile

import android.app.Application
import com.example.mobile.session.SessionManager

class MobileApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SessionManager.init(this)
    }
}
