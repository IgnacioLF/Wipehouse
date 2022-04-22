package com.example.wipehouse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import java.sql.BatchUpdateException

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
            startActivity(Intent(applicationContext,Register::class.java))
        }

        var buttonacceder = findViewById<Button>(R.id.buttonAcceder)
        buttonacceder.setOnClickListener {
         login()
        }
    }

    fun login(){
        var edittextemail = findViewById<EditText>(R.id.editTextEmail)
        var edittextpassword = findViewById<EditText>(R.id.editTextTextPassword)
        if (edittextemail.text.isNotEmpty()&&edittextpassword.text.isNotEmpty()){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(edittextemail.toString(),edittextpassword.toString()).addOnCompleteListener {
            if (it.isSuccessful){
                //exito
            }else{

            }
        }
        } else {
            Toast.makeText(applicationContext,"Error alguno de los campos esta vacio",Toast.LENGTH_SHORT)
        }
    }

    fun errorlogin(){

    }
}