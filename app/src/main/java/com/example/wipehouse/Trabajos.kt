package com.example.wipehouse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout

class Trabajos : AppCompatActivity() {
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