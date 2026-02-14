package com.example.mobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mobile.session.SessionManager

class MainActivity : AppCompatActivity() {

    private lateinit var homeLead: TextView
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var btnDashboard: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        homeLead = findViewById(R.id.home_lead)
        btnLogin = findViewById(R.id.btn_login)
        btnRegister = findViewById(R.id.btn_register)
        btnDashboard = findViewById(R.id.btn_dashboard)

        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        btnDashboard.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

        updateUi()
    }

    override fun onResume() {
        super.onResume()
        updateUi()
    }

    private fun updateUi() {
        val loggedIn = SessionManager.isLoggedIn()
        if (loggedIn) {
            homeLead.text = "You're signed in. Head to your dashboard to manage your account."
            btnLogin.visibility = View.GONE
            btnRegister.visibility = View.GONE
            btnDashboard.visibility = View.VISIBLE
        } else {
            homeLead.text = "Sign in to your account or create one to get started."
            btnLogin.visibility = View.VISIBLE
            btnRegister.visibility = View.VISIBLE
            btnDashboard.visibility = View.GONE
        }
    }
}
