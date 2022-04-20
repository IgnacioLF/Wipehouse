package com.example.wipehouse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var buttonCliente = findViewById<Button>(R.id.buttonCliente)
        var buttonTrabajador = findViewById<Button>(R.id.buttonTrabajador)
        var linearparte1 = findViewById<LinearLayout>(R.id.linearparte1)
        var linearparte2 = findViewById<LinearLayout>(R.id.linearparte2)
        var textViewparte1 = findViewById<TextView>(R.id.textViewparte1)
        var textViewparte2 = findViewById<TextView>(R.id.textViewparte2)
        var darkbluebackground = findViewById<ImageView>(R.id.imageViewfondo_darkblue)
        buttonCliente.setOnClickListener {
            linearparte1.setVisibility(View.GONE)
            linearparte2.setVisibility(View.VISIBLE)
            textViewparte1.setVisibility(View.GONE)
            textViewparte2.setVisibility(View.VISIBLE)
            darkbluebackground.setVisibility(View.GONE)
        }
        buttonTrabajador.setOnClickListener {
            linearparte1.setVisibility(View.GONE)
            linearparte2.setVisibility(View.VISIBLE)
            textViewparte1.setVisibility(View.GONE)
            textViewparte2.setVisibility(View.VISIBLE)
            darkbluebackground.setVisibility(View.GONE)
        }
    }
}