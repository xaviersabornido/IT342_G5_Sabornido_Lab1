package com.example.mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.mobile.api.ApiClient
import com.example.mobile.session.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (!SessionManager.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK })
            finish()
            return
        }

        val user = SessionManager.getUser()
        if (user != null) {
            findViewById<TextView>(R.id.dashboard_user_badge).text = user.username ?: ""
            findViewById<TextView>(R.id.dashboard_username).text = user.username ?: ""
            findViewById<TextView>(R.id.dashboard_email).text = user.email ?: ""
            findViewById<TextView>(R.id.dashboard_created_at).text = user.createdAt?.let { parseDate(it) } ?: "N/A"
            findViewById<TextView>(R.id.dashboard_roles).text = user.roles?.joinToString(", ")?.ifEmpty { "USER" } ?: "USER"
        }

        findViewById<Button>(R.id.btn_logout).setOnClickListener {
            lifecycleScope.launch {
                try {
                    withContext(Dispatchers.IO) { ApiClient.authApi.logout() }
                } catch (_: Exception) { }
                SessionManager.logout()
                startActivity(Intent(this@DashboardActivity, MainActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK })
                finish()
            }
        }
    }

    private fun parseDate(iso: String): String {
        return try {
            val t = iso.replace("T", " ").substringBefore(".")
            val parts = t.split(" ")
            if (parts.size >= 1) {
                val dateParts = parts[0].split("-")
                if (dateParts.size >= 3) {
                    "${dateParts[1]}/${dateParts[2]}/${dateParts[0]}"
                } else parts[0]
            } else iso
        } catch (_: Exception) {
            iso
        }
    }
}
