package com.example.wipehouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ScrollView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [User_buscar.newInstance] factory method to
 * create an instance of this fragment.
 */
class User_buscar : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var vista = inflater.inflate(R.layout.fragment_user_buscar, container, false)
        var scrollseleccion = vista.findViewById<ScrollView>(R.id.scrollseleccion)
        var scrolllist = vista.findViewById<ScrollView>(R.id.scrolllist)
        var imageButtonbackarrow = vista.findViewById<ImageButton>(R.id.imageButtonbackarrow)
        var buttonaltacocina = vista.findViewById<Button>(R.id.buttonaltacocina)
        var buttoncocinatradicional = vista.findViewById<Button>(R.id.buttoncocinatradicional)
        var buttoncocinalowcost = vista.findViewById<Button>(R.id.buttoncocinalowcost)
        var buttonpiscinagrande = vista.findViewById<Button>(R.id.buttonpiscinagrande)
        var buttonpiscinasmediana = vista.findViewById<Button>(R.id.buttonpiscinasmediana)
        var buttonpiscinaspequenas = vista.findViewById<Button>(R.id.buttonpiscinaspequenas)
        var buttonlimpiador = vista.findViewById<Button>(R.id.buttonlimpiador)
        var buttoncortacesped = vista.findViewById<Button>(R.id.buttoncortacesped)
        buttonaltacocina.setOnClickListener{
            scrollseleccion.setVisibility(View.GONE)
            scrolllist.setVisibility(View.VISIBLE) }
        buttoncocinatradicional.setOnClickListener {
            scrollseleccion.setVisibility(View.GONE)
            scrolllist.setVisibility(View.VISIBLE) }
        buttoncocinalowcost.setOnClickListener {
            scrollseleccion.setVisibility(View.GONE)
            scrolllist.setVisibility(View.VISIBLE) }
        buttonpiscinagrande.setOnClickListener {
            scrollseleccion.setVisibility(View.GONE)
            scrolllist.setVisibility(View.VISIBLE) }
        buttonpiscinasmediana.setOnClickListener {
            scrollseleccion.setVisibility(View.GONE)
            scrolllist.setVisibility(View.VISIBLE) }
        buttonpiscinaspequenas.setOnClickListener {
            scrollseleccion.setVisibility(View.GONE)
            scrolllist.setVisibility(View.VISIBLE) }
        buttonlimpiador.setOnClickListener {
            scrollseleccion.setVisibility(View.GONE)
            scrolllist.setVisibility(View.VISIBLE) }
        buttoncortacesped.setOnClickListener {
            scrollseleccion.setVisibility(View.GONE)
            scrolllist.setVisibility(View.VISIBLE) }
        imageButtonbackarrow.setOnClickListener {
            scrollseleccion.setVisibility(View.VISIBLE)
            scrolllist.setVisibility(View.GONE)
        }

        return vista
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment User_buscar.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            User_buscar().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}