package com.example.wipehouse

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class Register : AppCompatActivity() {

    lateinit var editTextTextNombre: EditText
    lateinit var editTextapellidos: EditText
    lateinit var editTextTextContraseña: EditText
    lateinit var editTextDNI: EditText
    lateinit var editTextEmail: EditText
    lateinit var editTextTelefono: EditText
    lateinit var editTextDireccion: EditText
    lateinit var editTextCodigopostal: EditText
    lateinit var linearparte1: LinearLayout
    lateinit var linearparte2: LinearLayout
    lateinit var textViewTitulo: TextView
    lateinit var textViewTitulo2: TextView
    lateinit var profile_image: de.hdodenhof.circleimageview.CircleImageView
    lateinit var imageButtonAddimage: ImageButton
    lateinit var spinnerciudad : Spinner
    var trabajador = false
    var cliente =false
    var partes=1
    lateinit var storagereference: StorageReference
    lateinit var imageUri :Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        storagereference= FirebaseStorage.getInstance().getReference()
        editTextTextNombre=findViewById(R.id.editTextTextNombre)
        editTextapellidos=findViewById(R.id.editTextapellidos)
        editTextTextContraseña=findViewById(R.id.editTextTextContraseña)
        editTextDNI=findViewById(R.id.editTextDNI)
        editTextEmail=findViewById(R.id.editTextEmail)
        editTextTelefono=findViewById(R.id.editTextTelefono)
        editTextDireccion=findViewById(R.id.editTextDireccion)
        editTextCodigopostal=findViewById(R.id.editTextCodigopostal)
        linearparte1=findViewById(R.id.linearparte1)
        linearparte2=findViewById(R.id.linearparte2)
        var imageButtonCliente = findViewById<ImageButton>(R.id.imageButtonCliente)
        var imageButtonTrabajador = findViewById<ImageButton>(R.id.imageButtonTrabajador)
        textViewTitulo=findViewById(R.id.textViewTitulo)
        textViewTitulo2=findViewById(R.id.textViewTitulo2)
        profile_image=findViewById(R.id.profile_image)
        imageButtonAddimage=findViewById(R.id.imageButtonAddimage)
        val backarrowB = findViewById<ImageButton>(R.id.imageButtonBackArrow)
        spinnerciudad = findViewById(R.id.spinnerciudad)
        var ciudadlista = arrayOf<String>("Madrid","Barcelona")
        var spinadapter = ArrayAdapter(this,R.layout.myselectedspinner,ciudadlista)
        spinadapter.setDropDownViewResource(R.layout.mydropdownspinner)
        spinnerciudad.adapter = spinadapter

        spinnerciudad.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        imageButtonAddimage.setOnClickListener {
            var openGalleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            launcher.launch(openGalleryIntent);
        }

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
            } else if (partes==3){
                linearparte2.setVisibility(View.VISIBLE)
                textViewTitulo.setVisibility(View.VISIBLE)
                textViewTitulo2.setVisibility(View.INVISIBLE)
                profile_image.setVisibility(View.INVISIBLE)
                imageButtonAddimage.setVisibility(View.INVISIBLE)
                partes=2
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
                //// TODO ciudad spinner not empty
                if (editTextDireccion.text.isNotEmpty()&&editTextCodigopostal.text.isNotEmpty()&&(trabajador==true||cliente==true)){
                    if(trabajador==true){
                        partes=3
                        linearparte2.setVisibility(View.INVISIBLE)
                        textViewTitulo.setVisibility(View.INVISIBLE)
                        textViewTitulo2.setVisibility(View.VISIBLE)
                        profile_image.setVisibility(View.VISIBLE)
                        imageButtonAddimage.setVisibility(View.VISIBLE)
                    }else{
                        registerCliente()
                    }
                } else {
                    Toast.makeText(applicationContext,"Error alguno de los campos esta vacio",Toast.LENGTH_LONG).show()
                }
            } else if (partes==3){
                if (::imageUri.isInitialized){
                    registerTrabajador()
                } else {
                    Toast.makeText(applicationContext,"Error necesitas subir una imagen",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private val launcher = registerForActivityResult(
        StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK
            && result.data != null
        ) {
            imageUri = result.data!!.data!!
            profile_image.setImageURI(imageUri);
        }
    }

    fun registerCliente(){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(editTextEmail.text.toString(),editTextTextContraseña.text.toString()).addOnCompleteListener {
            if(it.isSuccessful){
                var db = Firebase.firestore
                var user = hashMapOf(
                    "nombre" to editTextTextNombre.text.toString(),
                    "apellidos" to editTextapellidos.text.toString(),
                    // TODO ciudad spinner
                //    "ciudad" to editTextCiudad.text.toString(),
                    "direccion" to editTextDireccion.text.toString(),
                    "cp" to editTextCodigopostal.text.toString(),
                    "telefono" to editTextTelefono.text.toString(),
                    "dni" to editTextDNI.text.toString()
                )
                db.collection("usuarios")
                    .document(editTextEmail.text.toString())
                    .set(user)
                    .addOnSuccessListener { documentReference ->
                        startActivity(Intent(applicationContext,MainActivity::class.java))
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
            textViewTitulo.setVisibility(View.VISIBLE)
            textViewTitulo2.setVisibility(View.INVISIBLE)
            profile_image.setVisibility(View.INVISIBLE)
            imageButtonAddimage.setVisibility(View.INVISIBLE)
            partes=1
        })
        val dialog = builder.create()
        dialog.show()
    }

    fun registerTrabajador(){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(editTextEmail.text.toString(),editTextTextContraseña.text.toString()).addOnCompleteListener {
            if(it.isSuccessful){
                var db = Firebase.firestore
                var user = hashMapOf(
                    "nombre" to editTextTextNombre.text.toString(),
                    "apellidos" to editTextapellidos.text.toString(),
                    // TODO ciudad spinner
                  //  "ciudad" to editTextCiudad.text.toString(),
                    "direccion" to editTextDireccion.text.toString(),
                    "cp" to editTextCodigopostal.text.toString(),
                    "telefono" to editTextTelefono.text.toString(),
                    "dni" to editTextDNI.text.toString()
                )
                db.collection("usuarios")
                    .document(editTextEmail.text.toString())
                    .set(user)
                    .addOnSuccessListener { documentReference ->
                        var fileRef = storagereference.child("Trabajadores/"+editTextEmail.text.toString()+".jpg")
                        fileRef.putFile(imageUri).addOnSuccessListener {
                            var data = hashMapOf(
                                "altacocina_desc" to "",
                                "altacocina_platos" to "",
                                "altacocina_precio" to "",
                                "cocinatradicional_desc" to "",
                                "cocinatradicional_platos" to "",
                                "cocinatradicional_precio" to "",
                                "cocinalowcost_desc" to "",
                                "cocinalowcost_platos" to "",
                                "limpiador_precio" to "",
                                "cortacesped_precio" to "",
                                "mantenimiento_precio_grande" to "",
                                "mantenimiento_precio_mediana" to "",
                                "mantenimiento_precio_pequena" to ""
                            )
                            db.collection("trabajadores").document(editTextEmail.text.toString()).set(data).addOnSuccessListener {
                                var intent = Intent(applicationContext,Trabajos::class.java)
                                finishAffinity()
                                startActivity(intent)
                            }.addOnFailureListener {
                                FirebaseAuth.getInstance().currentUser?.delete()
                                db.collection("trabajadores").document(editTextEmail.text.toString()).delete()
                                db.collection("usuarios").document(editTextEmail.text.toString()).delete()
                                error("Se ha producido un error en la operacion")
                            }
                        }.addOnFailureListener{
                            FirebaseAuth.getInstance().currentUser?.delete()
                            db.collection("usuarios").document(editTextEmail.text.toString()).delete()
                            error("Se ha producido un error en la operacion")
                        }
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
    // no back arrow
    override fun onBackPressed() {}
}