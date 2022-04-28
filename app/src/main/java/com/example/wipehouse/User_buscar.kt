package com.example.wipehouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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
        var scrolllist = vista.findViewById<LinearLayout>(R.id.scrolllist)
        var imageButtonbackarrow = vista.findViewById<ImageButton>(R.id.imageButtonbackarrow)
        var buttonaltacocina = vista.findViewById<Button>(R.id.buttonaltacocina)
        var buttoncocinatradicional = vista.findViewById<Button>(R.id.buttoncocinatradicional)
        var buttoncocinalowcost = vista.findViewById<Button>(R.id.buttoncocinalowcost)
        var buttonpiscinagrande = vista.findViewById<Button>(R.id.buttonpiscinagrande)
        var buttonpiscinasmediana = vista.findViewById<Button>(R.id.buttonpiscinasmediana)
        var buttonpiscinaspequenas = vista.findViewById<Button>(R.id.buttonpiscinaspequenas)
        var buttonlimpiador = vista.findViewById<Button>(R.id.buttonlimpiador)
        var buttoncortacesped = vista.findViewById<Button>(R.id.buttoncortacesped)
        var textselecciona = vista.findViewById<TextView>(R.id.textselecciona)
        var listview = vista.findViewById<ListView>(R.id.listviewbuscar)
        var listatrabajadores=ArrayList<Trabajador>()
        fun gotolista(){
            scrollseleccion.setVisibility(View.GONE)
            textselecciona.setVisibility(View.GONE)
            scrolllist.setVisibility(View.VISIBLE) }
        var db = Firebase.firestore
        db.collection("trabajadores").get().addOnSuccessListener { result ->
            for (document in result){
                var trabajadorciudad = document.data["ciudad"].toString()
                var imagenurl = document.data["imageurl"].toString()
                var altacocina_desc = document.data["altacocina_desc"].toString()
                var altacocina_platos = document.data["altacocina_platos"].toString()
                var altacocina_precio = document.data["altacocina_precio"].toString()
                var cocinatradicional_desc = document.data["cocinatradicional_desc"].toString()
                var cocinatradicional_platos = document.data["cocinatradicional_platos"].toString()
                var cocinatradicional_precio = document.data["cocinatradicional_precio"].toString()
                var cocinalowcost_desc = document.data["cocinalowcost_desc"].toString()
                var cocinalowcost_platos = document.data["cocinalowcost_platos"].toString()
                var cocinalowcost_precio = document.data["cocinalowcost_precio"].toString()
                var limpiador_precio = document.data["limpiador_precio"].toString()
                var cortacesped_precio = document.data["cortacesped_precio"].toString()
                var mantenimiento_precio_grande = document.data["mantenimiento_precio_grande"].toString()
                var mantenimiento_precio_mediana = document.data["mantenimiento_precio_mediana"].toString()
                var mantenimiento_precio_pequena = document.data["mantenimiento_precio_pequena"].toString()
                var puntuacion_media = document.data["puntuacion_media"].toString()
                var emailtrabajador = document.id
                var nombreyapellido = document.data["nombreyapellido"].toString()
                val currecttrabajador=Trabajador(emailtrabajador,nombreyapellido,trabajadorciudad,imagenurl,altacocina_desc,altacocina_platos,altacocina_precio,cocinatradicional_desc,cocinatradicional_platos,cocinatradicional_precio,cocinalowcost_desc,cocinalowcost_platos,cocinalowcost_precio,limpiador_precio,cortacesped_precio,mantenimiento_precio_grande,mantenimiento_precio_mediana,mantenimiento_precio_pequena,puntuacion_media)
                listatrabajadores.add(currecttrabajador)
            }
        }

        buttonaltacocina.setOnClickListener{
            listview.adapter = context?.let { it1 -> TrabajadorBuscarArrayAdapter(it1,R.layout.item_list_trabajador_buscar,listatrabajadores,"Alta Cocina") }
            gotolista()}
        buttoncocinatradicional.setOnClickListener {
            listview.adapter = context?.let { it1 -> TrabajadorBuscarArrayAdapter(it1,R.layout.item_list_trabajador_buscar,listatrabajadores,"Cocina Tradicional")}
            gotolista()}
        buttoncocinalowcost.setOnClickListener {
            listview.adapter = context?.let { it1 -> TrabajadorBuscarArrayAdapter(it1,R.layout.item_list_trabajador_buscar,listatrabajadores,"Cocina Lowcost") }
            gotolista()}
        buttonpiscinagrande.setOnClickListener {
            gotolista()}
        buttonpiscinasmediana.setOnClickListener {
            gotolista()}
        buttonpiscinaspequenas.setOnClickListener {
            gotolista()}
        buttonlimpiador.setOnClickListener {
            gotolista()}
        buttoncortacesped.setOnClickListener {
            gotolista()}
        imageButtonbackarrow.setOnClickListener {
            scrollseleccion.setVisibility(View.VISIBLE)
            textselecciona.setVisibility(View.VISIBLE)
            scrolllist.setVisibility(View.GONE) }
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