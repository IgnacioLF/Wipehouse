package com.example.wipehouse

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class Trabajos : AppCompatActivity() {
    var cocinero = false
    var cortacesped = false
    var limpiador = false
    var mantenimiento = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trabajos)
        var linearaltacocina = findViewById<LinearLayout>(R.id.linearaltacocina)
        var checkBoxaltacocina = findViewById<CheckBox>(R.id.checkBoxaltacocina)
        var linearaltacocina_desc = findViewById<LinearLayout>(R.id.linearaltacocina_desc)
        var linearcocinatradicional = findViewById<LinearLayout>(R.id.linearcocinatradicional)
        var checkBoxcocinatradicional = findViewById<CheckBox>(R.id.checkBoxcocinatradicional)
        var linearcocinatradicional_desc = findViewById<LinearLayout>(R.id.linearcocinatradicional_desc)
        var linearcocinalowcost = findViewById<LinearLayout>(R.id.linearcocinalowcost)
        var checkBoxcocinalowcost = findViewById<CheckBox>(R.id.checkBoxcocinalowcost)
        var linearlowcostcocina_desc = findViewById<LinearLayout>(R.id.linearcocinalowcost_desc)

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
    }
}