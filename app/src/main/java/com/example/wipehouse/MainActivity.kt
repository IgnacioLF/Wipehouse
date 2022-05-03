package com.example.wipehouse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
        val user = FirebaseAuth.getInstance().currentUser
        // TODO diferenciar entre trabajador y empleado
        if (user != null) {
            startActivity(Intent(applicationContext,User_MainActivity::class.java))
        }
        buttonCliente.setOnClickListener {
            linearparte1.setVisibility(View.GONE)
            linearparte2.setVisibility(View.VISIBLE)
            textViewparte1.setVisibility(View.GONE)
            textViewparte2.setVisibility(View.VISIBLE)
            darkbluebackground.setVisibility(View.GONE)
            startActivity(Intent(applicationContext,User_MainActivity::class.java))
        }
        buttonTrabajador.setOnClickListener {
            linearparte1.setVisibility(View.GONE)
            linearparte2.setVisibility(View.VISIBLE)
            textViewparte1.setVisibility(View.GONE)
            textViewparte2.setVisibility(View.VISIBLE)
            darkbluebackground.setVisibility(View.GONE)
        }

        var buttonacceder = findViewById<Button>(R.id.buttonAcceder)
        buttonacceder.setOnClickListener {
         login()
        }
        var buttonregistrarse = findViewById<Button>(R.id.buttonRegistrarse)
        buttonregistrarse.setOnClickListener {
            startActivity(Intent(applicationContext,Register::class.java))
        }
    }

    fun login(){
        var db = Firebase.firestore
        var edittextemail = findViewById<EditText>(R.id.editTextEmail)
        var edittextpassword = findViewById<EditText>(R.id.editTextTextPassword)
        if (edittextemail.text.isNotEmpty()&&edittextpassword.text.isNotEmpty()){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(edittextemail.text.toString(),edittextpassword.text.toString()).addOnCompleteListener {
            if (it.isSuccessful){
                db.collection("trabajadores").document(edittextemail.text.toString()).get().addOnCompleteListener {
                    if (it.isSuccessful){
                        var document = it.getResult()
                        if (document.exists()){
                            startActivity(Intent(applicationContext,Trabajador_MainActivity::class.java))
                        } else{
                            startActivity(Intent(applicationContext,User_MainActivity::class.java))
                        }
                    } else {
                        errorlogin()
                    }
                }
            }else{
                errorlogin()
            }
        }
        } else {
            Toast.makeText(applicationContext,"Error alguno de los campos esta vacio",Toast.LENGTH_SHORT).show()
        }
    }

    fun errorlogin(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error alguno de sus campos son incorrectos")
        builder.setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
    }
}