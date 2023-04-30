package com.example.userlogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignUpActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        title = "Sign Up"

        nameEditText = findViewById(R.id.signup_name)
        emailEditText = findViewById(R.id.signup_email)
        phoneEditText = findViewById(R.id.signup_phone)
        passwordEditText = findViewById(R.id.signup_password)
        confirmPasswordEditText = findViewById(R.id.signup_confirm_password)
        signUpButton = findViewById(R.id.signup_button)
        databaseHelper = DatabaseHelper(this)

        signUpButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                val user = databaseHelper.getUserByEmail(email)
                if (user != null) {
                    Toast.makeText(this, "Email is already taken", Toast.LENGTH_SHORT).show()
                } else {
                    val id = databaseHelper.addUser(name, email, phone, password)
                    if (id == -1L) {
                        Toast.makeText(this, "Error adding user", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }
}