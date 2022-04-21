package com.example.wipehouse

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val backarrowB = findViewById<ImageButton>(R.id.imageButtonBackArrow)
        backarrowB.setOnClickListener {
            startActivity(Intent(applicationContext,MainActivity::class.java))
        }
    }
}