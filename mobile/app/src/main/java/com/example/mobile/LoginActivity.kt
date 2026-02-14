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
import androidx.lifecycle.lifecycleScope
import com.example.mobile.api.ApiClient
import com.example.mobile.api.LoginRequest
import com.example.mobile.session.SessionManager
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var errorText: TextView
    private lateinit var signInButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        usernameInput = findViewById(R.id.login_username)
        passwordInput = findViewById(R.id.login_password)
        errorText = findViewById(R.id.login_error)
        signInButton = findViewById(R.id.btn_sign_in)

        findViewById<Button>(R.id.btn_go_register).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        signInButton.setOnClickListener { doLogin() }
    }

    private fun doLogin() {
        val username = usernameInput.text.toString().trim()
        val password = passwordInput.text.toString()

        if (username.isEmpty()) {
            showError("Username is required")
            return
        }
        if (password.isEmpty()) {
            showError("Password is required")
            return
        }

        showError(null)
        signInButton.isEnabled = false

        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiClient.authApi.login(LoginRequest(username, password))
                }
                if (response.isSuccessful) {
                    val body = response.body()!!
                    SessionManager.saveToken(body.token)
                    SessionManager.saveUser(body.user)
                    startActivity(Intent(this@LoginActivity, DashboardActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK })
                    finish()
                } else {
                    val msg = response.errorBody()?.string()?.let { parseError(it) } ?: "Invalid username or password"
                    showError(msg)
                }
            } catch (e: Exception) {
                showError(e.message ?: "Network error")
            } finally {
                signInButton.isEnabled = true
            }
        }
    }

    private fun showError(message: String?) {
        if (message.isNullOrEmpty()) {
            errorText.visibility = View.GONE
            errorText.text = ""
        } else {
            errorText.text = message
            errorText.visibility = View.VISIBLE
        }
    }

    private fun parseError(body: String): String {
        return try {
            val json = com.google.gson.JsonParser.parseString(body).asJsonObject
            json.get("error")?.asString
                ?: json.getAsJsonObject("details")?.get("username")?.asString
                ?: json.getAsJsonObject("details")?.get("email")?.asString
                ?: "Request failed"
        } catch (_: Exception) {
            "Request failed"
        }
    }
}
