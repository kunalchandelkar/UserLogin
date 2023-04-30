package com.example.userlogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        title = "Login"

        emailEditText = findViewById(R.id.login_email)
        passwordEditText = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.login_button)
        databaseHelper = DatabaseHelper(this)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email and password are required", Toast.LENGTH_SHORT).show()
            } else {
                val user = databaseHelper.getUserByEmail(email)
                if (user == null || user.password != password) {
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Welcome ${user.name}!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}

