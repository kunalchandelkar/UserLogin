package com.example.userlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button_next)

        button.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            intent.putExtra("message", "Hello from MainActivity!")
            startActivity(intent)
        }

        val button2 = findViewById<Button>(R.id.button_next2)

        button2.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("message", "Hello from MainActivity to Login!")
            startActivity(intent)
        }
    }
}