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
import com.example.mobile.api.RegisterRequest
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private lateinit var usernameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var confirmPasswordInput: TextInputEditText
    private lateinit var errorText: TextView
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        usernameInput = findViewById(R.id.register_username)
        emailInput = findViewById(R.id.register_email)
        passwordInput = findViewById(R.id.register_password)
        confirmPasswordInput = findViewById(R.id.register_confirm_password)
        errorText = findViewById(R.id.register_error)
        registerButton = findViewById(R.id.btn_register_submit)

        findViewById<Button>(R.id.btn_go_login).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        registerButton.setOnClickListener { doRegister() }
    }

    private fun doRegister() {
        val username = usernameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString()
        val confirmPassword = confirmPasswordInput.text.toString()

        if (username.isEmpty()) {
            showError("Username is required")
            return
        }
        if (username.length < 3) {
            showError("Username must be at least 3 characters")
            return
        }
        if (email.isEmpty()) {
            showError("Email is required")
            return
        }
        if (password.isEmpty()) {
            showError("Password is required")
            return
        }
        if (password.length < 6) {
            showError("Password must be at least 6 characters")
            return
        }
        if (password != confirmPassword) {
            showError("Passwords do not match")
            return
        }

        showError(null)
        registerButton.isEnabled = false

        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiClient.authApi.register(RegisterRequest(username, email, password))
                }
                if (response.isSuccessful) {
                    // Don't auto-login; send user to login (same as web)
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK })
                    finish()
                } else {
                    val msg = response.errorBody()?.string()?.let { parseError(it) } ?: "Registration failed"
                    showError(msg)
                }
            } catch (e: Exception) {
                showError(e.message ?: "Network error")
            } finally {
                registerButton.isEnabled = true
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
                ?: "Registration failed"
        } catch (_: Exception) {
            "Registration failed"
        }
    }
}
