package com.example.wipehouse

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Trabajos : AppCompatActivity() {
    lateinit var editTextlimpiadorprecio : EditText
    lateinit var editTextcortacespedprecio : EditText
    lateinit var editTextpreciopiscinagrande : EditText
    lateinit var editTextpreciopiscinamedianas : EditText
    lateinit var editTextpreciopiscinapequenas : EditText
    lateinit var editTextaltacocinadesc : EditText
    lateinit var editTextaltacocinaplatos : EditText
    lateinit var editTextaltacocinaprecio : EditText
    lateinit var editTextcocinatradicionaldesc : EditText
    lateinit var editTextcocinatradicionalplatos : EditText
    lateinit var editTextcocinatradicionalprecio : EditText
    lateinit var editTextcocinalowcostdesc : EditText
    lateinit var editTextcocinalowcostplatos : EditText
    lateinit var editTextcocinalowcostprecio : EditText

    var cocinero = false
    var cortacesped = false
    var limpiador = false
    var mantenimiento = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trabajos)
        var empty = true
        //edittext
        editTextlimpiadorprecio = findViewById(R.id.editTextlimpiadorprecio)
        editTextcortacespedprecio = findViewById(R.id.editTextcortacespedprecio)
        editTextpreciopiscinagrande = findViewById(R.id.editTextpreciopiscinagrande)
        editTextpreciopiscinamedianas = findViewById(R.id.editTextpreciopiscinamedianas)
        editTextpreciopiscinapequenas = findViewById(R.id.editTextpreciopiscinapequenas)
        editTextaltacocinadesc = findViewById(R.id.editTextaltacocinadesc)
        editTextaltacocinaplatos = findViewById(R.id.editTextaltacocinaplatos)
        editTextaltacocinaprecio = findViewById(R.id.editTextaltacocinaprecio)
        editTextcocinatradicionaldesc = findViewById(R.id.editTextcocinatradicionaldesc)
        editTextcocinatradicionalplatos = findViewById(R.id.editTextcocinatradicionalplatos)
        editTextcocinatradicionalprecio = findViewById(R.id.editTextcocinatradicionalprecio)
        editTextcocinalowcostdesc = findViewById(R.id.editTextcocinalowcostdesc)
        editTextcocinalowcostplatos = findViewById(R.id.editTextcocinalowcostplatos)
        editTextcocinalowcostprecio = findViewById(R.id.editTextcocinalowcostprecio)
        // checkbox de cocina
        var linearaltacocina = findViewById<LinearLayout>(R.id.linearaltacocina)
        var checkBoxaltacocina = findViewById<CheckBox>(R.id.checkBoxaltacocina)
        var linearaltacocina_desc = findViewById<LinearLayout>(R.id.linearaltacocina_desc)
        var linearcocinatradicional = findViewById<LinearLayout>(R.id.linearcocinatradicional)
        var checkBoxcocinatradicional = findViewById<CheckBox>(R.id.checkBoxcocinatradicional)
        var linearcocinatradicional_desc = findViewById<LinearLayout>(R.id.linearcocinatradicional_desc)
        var linearcocinalowcost = findViewById<LinearLayout>(R.id.linearcocinalowcost)
        var checkBoxcocinalowcost = findViewById<CheckBox>(R.id.checkBoxcocinalowcost)
        var linearlowcostcocina_desc = findViewById<LinearLayout>(R.id.linearcocinalowcost_desc)
        // genear visibility
        var imageButtonBackArrow = findViewById<ImageButton>(R.id.imageButtonBackArrow)
        var imageViewlogo = findViewById<ImageView>(R.id.imageViewlogo)
        var linearseleccione = findViewById<LinearLayout>(R.id.linearseleccione)
        var titulomantenimiento= findViewById<LinearLayout>(R.id.titulomantenimiento)
        var textViewLimpiador= findViewById<TextView>(R.id.textViewLimpiador)
        var textViewCortacesped = findViewById<TextView>(R.id.textViewCortacesped)
        var textViewTituloCocinero = findViewById<TextView>(R.id.textViewTituloCocinero)
        var linearmianbotones = findViewById<LinearLayout>(R.id.linearmianbotones)
        var linearlimpiador = findViewById<LinearLayout>(R.id.linearlimpiador)
        var linearcortacesped = findViewById<LinearLayout>(R.id.linearcortacesped)
        var linearmantenimiento = findViewById<LinearLayout>(R.id.linearmantenimiento)
        var scrollViewcocineromain= findViewById<ScrollView>(R.id.scrollViewcocineromain)
        var buttonGuardar = findViewById<Button>(R.id.buttonGuardar)
        var buttonFinalizar = findViewById<Button>(R.id.buttonFinalizar)
        var imageButtonMantenimiento = findViewById<ImageButton>(R.id.imageButtonMantenimiento)
        var imageButtonCortacesped = findViewById<ImageButton>(R.id.imageButtonCortacesped)
        var imageButtonLimpiador = findViewById<ImageButton>(R.id.imageButtonLimpiador)
        var imageButtonCocinero = findViewById<ImageButton>(R.id.imageButtonCocinero)

        imageButtonCocinero.setOnClickListener {
            linearmianbotones.setVisibility(View.GONE)
            linearseleccione.setVisibility(View.GONE)
            buttonFinalizar.setVisibility(View.GONE)
            imageViewlogo.setImageResource(R.drawable.cocinero_icon)
            imageViewlogo.setVisibility(View.VISIBLE)
            scrollViewcocineromain.setVisibility(View.VISIBLE)
            textViewTituloCocinero.setVisibility(View.VISIBLE)
            buttonGuardar.setVisibility(View.VISIBLE)
            cocinero=true
        }

        imageButtonCortacesped.setOnClickListener {
            linearmianbotones.setVisibility(View.GONE)
            linearseleccione.setVisibility(View.GONE)
            buttonFinalizar.setVisibility(View.GONE)
            imageViewlogo.setImageResource(R.drawable.cortacesped_icon)
            imageViewlogo.setVisibility(View.VISIBLE)
            linearcortacesped.setVisibility(View.VISIBLE)
            textViewCortacesped.setVisibility(View.VISIBLE)
            buttonGuardar.setVisibility(View.VISIBLE)
            cortacesped=true
        }

        imageButtonLimpiador.setOnClickListener {
            linearmianbotones.setVisibility(View.GONE)
            linearseleccione.setVisibility(View.GONE)
            buttonFinalizar.setVisibility(View.GONE)
            imageViewlogo.setImageResource(R.drawable.limpiador_icon)
            imageViewlogo.setVisibility(View.VISIBLE)
            linearlimpiador.setVisibility(View.VISIBLE)
            textViewLimpiador.setVisibility(View.VISIBLE)
            buttonGuardar.setVisibility(View.VISIBLE)
            limpiador=true
        }

        imageButtonMantenimiento.setOnClickListener {
            linearmianbotones.setVisibility(View.GONE)
            linearseleccione.setVisibility(View.GONE)
            buttonFinalizar.setVisibility(View.GONE)
            imageViewlogo.setImageResource(R.drawable.mantenimiento_icon)
            imageViewlogo.setVisibility(View.VISIBLE)
            linearmantenimiento.setVisibility(View.VISIBLE)
            titulomantenimiento.setVisibility(View.VISIBLE)
            buttonGuardar.setVisibility(View.VISIBLE)
            mantenimiento=true
        }

        imageButtonBackArrow.setOnClickListener {
            if (cocinero==true){
                linearmianbotones.setVisibility(View.VISIBLE)
                linearseleccione.setVisibility(View.VISIBLE)
                buttonFinalizar.setVisibility(View.VISIBLE)
                imageViewlogo.setVisibility(View.GONE)
                scrollViewcocineromain.setVisibility(View.GONE)
                textViewTituloCocinero.setVisibility(View.GONE)
                buttonGuardar.setVisibility(View.GONE)
                cocinero=false
            } else if (cortacesped==true){
                linearmianbotones.setVisibility(View.VISIBLE)
                linearseleccione.setVisibility(View.VISIBLE)
                buttonFinalizar.setVisibility(View.VISIBLE)
                imageViewlogo.setVisibility(View.GONE)
                linearcortacesped.setVisibility(View.GONE)
                textViewCortacesped.setVisibility(View.GONE)
                buttonGuardar.setVisibility(View.GONE)
                cortacesped=false
            } else if (limpiador==true){
                linearmianbotones.setVisibility(View.VISIBLE)
                linearseleccione.setVisibility(View.VISIBLE)
                buttonFinalizar.setVisibility(View.VISIBLE)
                imageViewlogo.setVisibility(View.GONE)
                linearlimpiador.setVisibility(View.GONE)
                textViewLimpiador.setVisibility(View.GONE)
                buttonGuardar.setVisibility(View.GONE)
                limpiador=false
            } else if (mantenimiento==true){
                linearmianbotones.setVisibility(View.VISIBLE)
                linearseleccione.setVisibility(View.VISIBLE)
                buttonFinalizar.setVisibility(View.VISIBLE)
                imageViewlogo.setVisibility(View.GONE)
                linearmantenimiento.setVisibility(View.GONE)
                titulomantenimiento.setVisibility(View.GONE)
                buttonGuardar.setVisibility(View.GONE)
                mantenimiento=false
            } else {
                // go to edit trabajador activity
            }
        }

        checkBoxaltacocina.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                linearaltacocina.setBackgroundResource(R.drawable.blue_roundcorners_low)
                linearaltacocina_desc.setVisibility(View.VISIBLE)
            } else {
                linearaltacocina.setBackgroundResource(R.drawable.white_roundcorners_low)
                linearaltacocina_desc.setVisibility(View.GONE)
            }
        }

        checkBoxcocinatradicional.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                linearcocinatradicional.setBackgroundResource(R.drawable.blue_roundcorners_low)
                linearcocinatradicional_desc.setVisibility(View.VISIBLE)
            } else {
                linearcocinatradicional.setBackgroundResource(R.drawable.white_roundcorners_low)
                linearcocinatradicional_desc.setVisibility(View.GONE)
            }
        }

        checkBoxcocinalowcost.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                linearcocinalowcost.setBackgroundResource(R.drawable.blue_roundcorners_low)
                linearlowcostcocina_desc.setVisibility(View.VISIBLE)
            } else {
                linearcocinalowcost.setBackgroundResource(R.drawable.white_roundcorners_low)
                linearlowcostcocina_desc.setVisibility(View.GONE)
            }
        }

        buttonGuardar.setOnClickListener {
            var data = hashMapOf<String,String>()
            if (cocinero==true){
                if (checkBoxaltacocina.isChecked){
                    if (editTextaltacocinadesc.text.isNotEmpty()&&editTextaltacocinaplatos.text.isNotEmpty()&&editTextaltacocinaprecio.text.isNotEmpty()){
                        empty=false
                        data.put("altacocina_desc",editTextaltacocinadesc.text.toString())
                        data.put("altacocina_platos",editTextaltacocinaplatos.text.toString())
                        data.put("altacocina_precio",editTextaltacocinaprecio.text.toString())
                    } else {
                        empty=true
                    }
                } else {
                    data.put("altacocina_desc","")
                    data.put("altacocina_platos","")
                    data.put("altacocina_precio","")
                }
                if (checkBoxcocinatradicional.isChecked){
                    if (editTextcocinatradicionaldesc.text.isNotEmpty()&&editTextcocinatradicionalplatos.text.isNotEmpty()&&editTextcocinatradicionalprecio.text.isNotEmpty()){
                        data.put("cocinatradicional_desc",editTextcocinatradicionaldesc.text.toString())
                        data.put("cocinatradicional_platos",editTextcocinatradicionalplatos.text.toString())
                        data.put("cocinatradicional_precio",editTextcocinatradicionalprecio.text.toString())
                        empty=false
                    } else{
                        empty=true
                    }
                } else {
                    data.put("cocinatradicional_desc","")
                    data.put("cocinatradicional_platos","")
                    data.put("cocinatradicional_precio","")
                }
                if (checkBoxcocinalowcost.isChecked){
                    if (editTextcocinalowcostdesc.text.isNotEmpty()&&editTextcocinalowcostplatos.text.isNotEmpty()&&editTextcocinalowcostprecio.text.isNotEmpty()){
                        data.put("cocinalowcost_desc",editTextcocinalowcostdesc.text.toString())
                        data.put("cocinalowcost_platos",editTextcocinalowcostplatos.text.toString())
                        data.put("cocinalowcost_precio",editTextcocinalowcostprecio.text.toString())
                        empty=false
                    } else {
                        empty=true
                    }
                } else {
                    data.put("cocinalowcost_desc","")
                    data.put("cocinalowcost_platos","")
                    data.put("cocinalowcost_precio","")
                }
                if (empty==false){
                    guardardatos(data)
                } else {
                    Toast.makeText(applicationContext,"Error alguno de los campos esta vacio",Toast.LENGTH_LONG).show()
                }
            } else if (cortacesped==true){
                data.put("cortacesped_precio",editTextcortacespedprecio.text.toString())
                guardardatos(data)
            } else if (limpiador==true){
                data.put("limpiador_precio",editTextlimpiadorprecio.text.toString())
                guardardatos(data)
            } else if (mantenimiento==true){
                data.put("mantenimiento_precio_grande",editTextpreciopiscinagrande.text.toString())
                data.put("mantenimiento_precio_mediana",editTextpreciopiscinamedianas.text.toString())
                data.put("mantenimiento_precio_pequena",editTextpreciopiscinapequenas.text.toString())
                guardardatos(data)
            }
        }
    }

    fun guardardatos(data : HashMap<String,String>){
        var db = Firebase.firestore
        var email = FirebaseAuth.getInstance().currentUser?.email
        if (email != null) {
            db.collection("trabajadores")
                .document(email)
                .update(data as Map<String, Any>)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(applicationContext,"Cambios guardados correctamente",Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(applicationContext,"Se ha poucido un error en la operacion",Toast.LENGTH_LONG).show()
                }
        }
    }
}