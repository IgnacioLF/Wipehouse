package com.example.wipehouse

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {

    lateinit var editTextTextNombre: EditText
    lateinit var editTextapellidos: EditText
    lateinit var editTextTextContraseña: EditText
    lateinit var editTextDNI: EditText
    lateinit var editTextEmail: EditText
    lateinit var editTextTelefono: EditText
    lateinit var editTextprovincia: EditText
    lateinit var editTextCiudad: EditText
    lateinit var editTextDireccion: EditText
    lateinit var editTextCodigopostal: EditText
    lateinit var linearparte1: LinearLayout
    lateinit var linearparte2: LinearLayout
    var trabajador = false
    var cliente =false
    var partes=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        editTextTextNombre=findViewById(R.id.editTextTextNombre)
        editTextapellidos=findViewById(R.id.editTextapellidos)
        editTextTextContraseña=findViewById(R.id.editTextTextContraseña)
        editTextDNI=findViewById(R.id.editTextDNI)
        editTextEmail=findViewById(R.id.editTextEmail)
        editTextTelefono=findViewById(R.id.editTextTelefono)
        editTextprovincia=findViewById(R.id.editTextprovincia)
        editTextCiudad=findViewById(R.id.editTextCiudad)
        editTextDireccion=findViewById(R.id.editTextDireccion)
        editTextCodigopostal=findViewById(R.id.editTextCodigopostal)
        linearparte1 = findViewById(R.id.linearparte1)
        linearparte2 = findViewById(R.id.linearparte2)
        var imageButtonCliente = findViewById<ImageButton>(R.id.imageButtonCliente)
        var imageButtonTrabajador = findViewById<ImageButton>(R.id.imageButtonTrabajador)

        val backarrowB = findViewById<ImageButton>(R.id.imageButtonBackArrow)
        imageButtonCliente.setOnClickListener {
            cliente=true
            trabajador=false
            imageButtonCliente.setImageResource(R.drawable.cliente_button_on_selected)
            imageButtonTrabajador.setImageResource(R.drawable.trabajador_button)
        }
        imageButtonTrabajador.setOnClickListener {
            cliente=false
            trabajador=true
            imageButtonCliente.setImageResource(R.drawable.cliente_button)
            imageButtonTrabajador.setImageResource(R.drawable.trabajador_button_on_selected)
        }
        backarrowB.setOnClickListener {
            if(partes==1){
                startActivity(Intent(applicationContext,MainActivity::class.java))
            } else if (partes==2){
                linearparte1.setVisibility(View.VISIBLE)
                linearparte2.setVisibility(View.INVISIBLE)
                partes=1
            }
        }

        val buttonContinuar=findViewById<Button>(R.id.buttonContinuar)
        buttonContinuar.setOnClickListener {
            if (partes==1){
                if (editTextTextNombre.text.isNotEmpty()&&editTextapellidos.text.isNotEmpty()&&editTextTextContraseña.text.isNotEmpty()&&editTextDNI.text.isNotEmpty()&&editTextEmail.text.isNotEmpty()&&editTextTelefono.text.isNotEmpty()){
                    linearparte1.setVisibility(View.INVISIBLE)
                    linearparte2.setVisibility(View.VISIBLE)
                    partes=2
                } else {
                    Toast.makeText(applicationContext,"Error alguno de los campos esta vacio",Toast.LENGTH_LONG).show()
                }
            } else if (partes==2){
                if (editTextprovincia.text.isNotEmpty()&&editTextCiudad.text.isNotEmpty()&&editTextDireccion.text.isNotEmpty()&&editTextCodigopostal.text.isNotEmpty()&&(trabajador==true||cliente==true)){
                    register()
                } else {
                    Toast.makeText(applicationContext,"Error alguno de los campos esta vacio",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun register(){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(editTextEmail.text.toString(),editTextTextContraseña.text.toString()).addOnCompleteListener {
            if(it.isSuccessful){
                var db = Firebase.firestore
                var user = hashMapOf(
                    "nombre" to editTextTextNombre.text.toString(),
                    "apellidos" to editTextapellidos.text.toString(),
                    "provincia" to editTextprovincia.text.toString(),
                    "ciudad" to editTextCiudad.text.toString(),
                    "direccion" to editTextDireccion.text.toString(),
                    "cp" to editTextCodigopostal.text.toString(),
                    "telefono" to editTextTelefono.text.toString(),
                    "dni" to editTextDNI.text.toString()
                )
                db.collection("usuarios")
                    .document(editTextEmail.text.toString())
                    .set(user)
                    .addOnSuccessListener { documentReference ->
                        startActivity(Intent(applicationContext,test::class.java))
                    }
                    .addOnFailureListener { e ->
                        FirebaseAuth.getInstance().currentUser?.delete()
                        error("Se ha producido un error en la operacion")
                    }

            } else{
                if (it.getException().toString().contains("The email address is already in use by another account")){
                    error("Se ha producido un error este email ya esta siendo utilizado")
                } else if (it.getException().toString().contains("The given password is invalid")){
                    error("Se ha producido un error la contraseña es erronea recuerda que tiene que contener al menos 6 carácteres")
                } else {
                    error("Se ha producido un error en la operacion")
                }
            }
        }
    }

    fun error(mensaje: String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialogInterface, i ->
            linearparte1.setVisibility(View.VISIBLE)
            linearparte2.setVisibility(View.INVISIBLE)
            partes=1
        })
        val dialog = builder.create()
        dialog.show()
    }

}