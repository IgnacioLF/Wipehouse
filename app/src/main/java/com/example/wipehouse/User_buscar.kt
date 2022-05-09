package com.example.wipehouse

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isEmpty
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

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
        var currentemailuser = FirebaseAuth.getInstance().currentUser?.email
        val options = RequestOptions().circleCrop()
        //buscador
        var imageButtonbuscar = vista.findViewById<ImageButton>(R.id.imageButtonbuscar)
        var editTexbuscador = vista.findViewById<EditText>(R.id.editTexbuscador)
        // cocina
        var linearbotonescocina = vista.findViewById<LinearLayout>(R.id.linearbotonescocina)
        var linearcocineromain = vista.findViewById<LinearLayout>(R.id.linearcocineromain)
        var imageViewcocinerologo = vista.findViewById<ImageView>(R.id.imageViewcocinerologo)
        // piscias
        var linearpiscinasmain = vista.findViewById<LinearLayout>(R.id.linearpiscinasmain)
        var imageViewpiscinaslogo = vista.findViewById<ImageView>(R.id.imageViewpiscinaslogo)
        var linearbotonespiscinas = vista.findViewById<LinearLayout>(R.id.linearbotonespiscinas)
        // limpiador
        var linearlimpiadormain = vista.findViewById<LinearLayout>(R.id.linearlimpiadormain)
        var imageViewlimpiadorlogo = vista.findViewById<ImageView>(R.id.imageViewlimpiadorlogo)
        // cortacesped
        var linearcortacespedmain = vista.findViewById<LinearLayout>(R.id.linearcortacespedmain)
        var imageViewcortacespedlogo = vista.findViewById<ImageView>(R.id.imageViewcortacespedlogo)

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
        var scrollrealizarpedido = vista.findViewById<ScrollView>(R.id.scrollrealizarpedido)
        // scroll realizar pedido data
        var imageView_trabajador = vista.findViewById<ImageView>(R.id.imageView_trabajador)
        var imageViewestrella1 = vista.findViewById<ImageView>(R.id.imageViewestrella1)
        var imageViewestrella2 = vista.findViewById<ImageView>(R.id.imageViewestrella2)
        var imageViewestrella3 = vista.findViewById<ImageView>(R.id.imageViewestrella3)
        var imageViewestrella4 = vista.findViewById<ImageView>(R.id.imageViewestrella4)
        var imageViewestrella5 = vista.findViewById<ImageView>(R.id.imageViewestrella5)
        var textViewNombreyapellido = vista.findViewById<TextView>(R.id.textViewNombreyapellido)
        var textViewCategoria = vista.findViewById<TextView>(R.id.textViewCategoria)
        var textViewprecio = vista.findViewById<TextView>(R.id.textViewprecio)
        var linneardescripcion = vista.findViewById<LinearLayout>(R.id.linneardescripcion)
        var textViewdescripcion = vista.findViewById<TextView>(R.id.textViewdescripcion)
        var linnearplatos = vista.findViewById<LinearLayout>(R.id.linnearplatos)
        var textViewplatos = vista.findViewById<TextView>(R.id.textViewplatos)
        var textViewncantidaddetit = vista.findViewById<TextView>(R.id.textViewncantidaddetit)
        var editTextcantidadde = vista.findViewById<EditText>(R.id.editTextcantidadde)
        var editTextfecha = vista.findViewById<EditText>(R.id.editTextfecha)
        var editTexthorainicio = vista.findViewById<EditText>(R.id.editTexthorainicio)
        var linearcantidadde = vista.findViewById<LinearLayout>(R.id.linearcantidadde)
        var imageButtonbackarrowrelizarpedido = vista.findViewById<ImageButton>(R.id.imageButtonbackarrowrelizarpedido)
        var textViewpreciofinal = vista.findViewById<TextView>(R.id.textViewpreciofinal)
        var buttonrealizarpedido = vista.findViewById<Button>(R.id.buttonrealizarpedido)
        var db = Firebase.firestore
        var nombreyapellido_user = ""
        var direccion_user = ""
        var imageViewlogoloading = vista.findViewById<ImageView>(R.id.imageViewlogoloading)
        var relativeloading = vista.findViewById<RelativeLayout>(R.id.relativeloading)
        var textViewisempty = vista.findViewById<TextView>(R.id.textViewisempty)
        Glide.with(this).load(R.drawable.loading_logo).into(imageViewlogoloading)

        var currentciudad = ""
        if (currentemailuser != null) {
            db.collection("usuarios").document(currentemailuser).get().addOnSuccessListener {
                    document ->
                currentciudad = document.data?.get("ciudad").toString()
                nombreyapellido_user = document.data?.get("nombre").toString() + " " + document.data?.get("apellidos").toString()
                if (document.data?.get("apellidos").toString().contains(" ")){
                    var apellidosarr = Pattern.compile(" ").split(document.data?.get("apellidos").toString())
                    nombreyapellido_user = document.data?.get("nombre").toString() + " " + apellidosarr[0]
                }
                direccion_user = document.data?.get("direccion").toString()
            }
        }
        fun mensajepopup(accion: String, mensaje: String) {
            val builder = context?.let { AlertDialog.Builder(it) }
            builder?.setTitle(accion)
            builder?.setMessage(mensaje)
            builder?.setPositiveButton("Aceptar", null)
            builder?.create()?.show()
        }

        fun gotolista(){
            scrollseleccion.setVisibility(View.GONE)
            textselecciona.setVisibility(View.GONE)
            scrolllist.setVisibility(View.VISIBLE)
            relativeloading.setVisibility(View.VISIBLE)
            listview.setVisibility(View.GONE)}

        fun closemenus(){
            linearcocineromain.setBackgroundResource(R.drawable.bluestroke_roundcorners_low)
            imageViewcocinerologo.setImageResource(R.drawable.cocinero_icon)
            linearbotonescocina.setVisibility(View.GONE)
            linearcortacespedmain.setBackgroundResource(R.drawable.bluestroke_roundcorners_low)
            imageViewcortacespedlogo.setImageResource(R.drawable.cortacesped_icon)
            buttoncortacesped.setVisibility(View.GONE)
            linearlimpiadormain.setBackgroundResource(R.drawable.bluestroke_roundcorners_low)
            imageViewlimpiadorlogo.setImageResource(R.drawable.limpiador_icon)
            buttonlimpiador.setVisibility(View.GONE)
            linearpiscinasmain.setBackgroundResource(R.drawable.bluestroke_roundcorners_low)
            imageViewpiscinaslogo.setImageResource(R.drawable.mantenimiento_icon)
            linearbotonespiscinas.setVisibility(View.GONE)
        }
        imageButtonbackarrowrelizarpedido.setOnClickListener {
            editTextcantidadde.setText("")
            editTextfecha.setText("")
            editTexthorainicio.setText("")
            scrolllist.setVisibility(View.VISIBLE)
            scrollrealizarpedido.setVisibility(View.GONE)
        }
        linearpiscinasmain.setOnClickListener {
            closemenus()
            linearpiscinasmain.setBackgroundResource(R.drawable.blue_roundcorners_low)
            imageViewpiscinaslogo.setImageResource(R.drawable.mantenimientoicon_darkblue)
            linearbotonespiscinas.setVisibility(View.VISIBLE)
        }

        linearlimpiadormain.setOnClickListener {
            closemenus()
            linearlimpiadormain.setBackgroundResource(R.drawable.blue_roundcorners_low)
            imageViewlimpiadorlogo.setImageResource(R.drawable.limpiadoricon_darkblue)
            buttonlimpiador.setVisibility(View.VISIBLE)
        }
        linearcortacespedmain.setOnClickListener {
            closemenus()
            linearcortacespedmain.setBackgroundResource(R.drawable.blue_roundcorners_low)
            imageViewcortacespedlogo.setImageResource(R.drawable.cortacespedicon_darkblue)
            buttoncortacesped.setVisibility(View.VISIBLE)
        }
        linearcocineromain.setOnClickListener {
            closemenus()
            linearcocineromain.setBackgroundResource(R.drawable.blue_roundcorners_low)
            imageViewcocinerologo.setImageResource(R.drawable.cocineroicon_darkblue)
            linearbotonescocina.setVisibility(View.VISIBLE)
        }

        var listatrabajadoresfiltrada=ArrayList<Trabajador>()

        fun getListratrabajadoresfiltrada(categoria_precio :String,categoria_lista :String){
            listatrabajadoresfiltrada.clear()
            db.collection("trabajadores").whereNotEqualTo(categoria_precio,"").get().addOnSuccessListener { result ->
                for (document in result) {
                    if (currentciudad.equals(document.data["ciudad"].toString())) {
                        var trabajadorciudad = document.data["ciudad"].toString()
                        var imagenurl = document.data["imageurl"].toString()
                        var altacocina_desc = document.data["altacocina_desc"].toString()
                        var altacocina_platos = document.data["altacocina_platos"].toString()
                        var altacocina_precio = document.data["altacocina_precio"].toString()
                        var cocinatradicional_desc =
                            document.data["cocinatradicional_desc"].toString()
                        var cocinatradicional_platos =
                            document.data["cocinatradicional_platos"].toString()
                        var cocinatradicional_precio =
                            document.data["cocinatradicional_precio"].toString()
                        var cocinalowcost_desc = document.data["cocinalowcost_desc"].toString()
                        var cocinalowcost_platos = document.data["cocinalowcost_platos"].toString()
                        var cocinalowcost_precio = document.data["cocinalowcost_precio"].toString()
                        var limpiador_precio = document.data["limpiador_precio"].toString()
                        var cortacesped_precio = document.data["cortacesped_precio"].toString()
                        var mantenimiento_precio_grande =
                            document.data["mantenimiento_precio_grande"].toString()
                        var mantenimiento_precio_mediana =
                            document.data["mantenimiento_precio_mediana"].toString()
                        var mantenimiento_precio_pequena =
                            document.data["mantenimiento_precio_pequena"].toString()
                        var puntuacion_media = document.data["puntuacion_media"].toString()
                        var emailtrabajador = document.id
                        var nombreyapellido = document.data["nombreyapellido"].toString()
                        val currecttrabajador = Trabajador(
                            emailtrabajador,
                            nombreyapellido,
                            trabajadorciudad,
                            imagenurl,
                            altacocina_desc,
                            altacocina_platos,
                            altacocina_precio,
                            cocinatradicional_desc,
                            cocinatradicional_platos,
                            cocinatradicional_precio,
                            cocinalowcost_desc,
                            cocinalowcost_platos,
                            cocinalowcost_precio,
                            limpiador_precio,
                            cortacesped_precio,
                            mantenimiento_precio_grande,
                            mantenimiento_precio_mediana,
                            mantenimiento_precio_pequena,
                            puntuacion_media
                        )
                        listatrabajadoresfiltrada.add(currecttrabajador)
                    }
                }
                listview.adapter = context?.let { it1 -> TrabajadorBuscarArrayAdapter(it1,R.layout.item_list_trabajador_buscar,listatrabajadoresfiltrada,categoria_lista) }
                relativeloading.setVisibility(View.GONE)
                listview.setVisibility(View.VISIBLE)
                listview.setEmptyView(textViewisempty)
                var listaonclick = ArrayList<Trabajador>()
                var listafiltrar = ArrayList<Trabajador>()
                imageButtonbuscar.setOnClickListener {
                    listafiltrar.clear()
                    listview.adapter = null
                    for (i in listatrabajadoresfiltrada.indices) {
                        if (listatrabajadoresfiltrada.get(i).nombreyapellido.lowercase().contains(editTexbuscador.text.toString().lowercase())) {
                            listafiltrar.add(listatrabajadoresfiltrada.get(i)) }
                    }
                    listview.adapter = context?.let { it1 -> TrabajadorBuscarArrayAdapter(it1, R.layout.item_list_trabajador_buscar, listafiltrar, categoria_lista) }
                    listaonclick = listafiltrar
                }

                if (listafiltrar.isEmpty()){
                    listaonclick = listatrabajadoresfiltrada
                } else{
                    listaonclick = listafiltrar
                }

                listview.setOnItemClickListener { parent, view, position, id ->
                    var precioporitem = 0
                    var preciofinal = 0
                    imageViewestrella1.setImageResource(R.drawable.estrellaicon_selected)
                    imageViewestrella2.setImageResource(R.drawable.estrellaicon_selected)
                    imageViewestrella3.setImageResource(R.drawable.estrellaicon_selected)
                    imageViewestrella4.setImageResource(R.drawable.estrellaicon_selected)
                    imageViewestrella5.setImageResource(R.drawable.estrellaicon_selected)
                    scrolllist.setVisibility(View.GONE)
                    scrollrealizarpedido.setVisibility(View.VISIBLE)
                    textViewNombreyapellido.text = listaonclick.get(position).nombreyapellido
                    Glide.with(vista).load(listaonclick.get(position).imageurl).apply(options).dontAnimate().into(imageView_trabajador)
                    when (listaonclick.get(position).puntucaion_media.toInt()) {
                        0 -> {imageViewestrella1.setImageResource(R.drawable.estrellaicon)
                            imageViewestrella2.setImageResource(R.drawable.estrellaicon)
                            imageViewestrella3.setImageResource(R.drawable.estrellaicon)
                            imageViewestrella4.setImageResource(R.drawable.estrellaicon)
                            imageViewestrella5.setImageResource(R.drawable.estrellaicon)}
                        1 -> {imageViewestrella2.setImageResource(R.drawable.estrellaicon)
                            imageViewestrella3.setImageResource(R.drawable.estrellaicon)
                            imageViewestrella4.setImageResource(R.drawable.estrellaicon)
                            imageViewestrella5.setImageResource(R.drawable.estrellaicon)}
                        2 ->{ imageViewestrella3.setImageResource(R.drawable.estrellaicon)
                            imageViewestrella4.setImageResource(R.drawable.estrellaicon)
                            imageViewestrella5.setImageResource(R.drawable.estrellaicon)}
                        3 ->{imageViewestrella4.setImageResource(R.drawable.estrellaicon)
                            imageViewestrella5.setImageResource(R.drawable.estrellaicon)}
                        4 ->imageViewestrella5.setImageResource(R.drawable.estrellaicon)
                    }
                    textViewCategoria.text=categoria_lista
                    when (categoria_lista) {
                        "Alta Cocina" -> {textViewprecio.text = listaonclick.get(position).altacocina_precio + "€"
                        precioporitem=Integer. parseInt(listaonclick.get(position).altacocina_precio)}
                        "Cocina Tradicional" -> {textViewprecio.text = listaonclick.get(position).cocinatradicional_precio + "€"
                            precioporitem=Integer. parseInt(listaonclick.get(position).cocinatradicional_precio)}
                        "Cocina Lowcost" -> {textViewprecio.text = listaonclick.get(position).cocinalowcost_precio + "€"
                            precioporitem=Integer. parseInt(listaonclick.get(position).cocinalowcost_precio)}
                        "Piscina Grande" -> {textViewprecio.text = listaonclick.get(position).mantenimiento_precio_grande + "€"
                            precioporitem=Integer. parseInt(listaonclick.get(position).mantenimiento_precio_grande)}
                        "Piscina Mediana" -> {textViewprecio.text = listaonclick.get(position).mantenimiento_precio_mediana + "€"
                            precioporitem=Integer. parseInt(listaonclick.get(position).mantenimiento_precio_mediana)}
                        "Piscina Pequeña" -> {textViewprecio.text = listaonclick.get(position).mantenimiento_precio_pequena + "€"
                            precioporitem=Integer. parseInt(listaonclick.get(position).mantenimiento_precio_pequena)}
                        "Limpiador" -> {textViewprecio.text = listaonclick.get(position).limpiador_precio + "€"
                            precioporitem=Integer. parseInt(listaonclick.get(position).limpiador_precio)}
                        "Cortacesped" -> {textViewprecio.text = listaonclick.get(position).cortacesped_precio + "€"
                            precioporitem=Integer. parseInt(listaonclick.get(position).cortacesped_precio)}
                    }
                    if (categoria_lista.contains("Cocina")){
                        linneardescripcion.setVisibility(View.VISIBLE)
                        linnearplatos.setVisibility(View.VISIBLE)
                        when (categoria_lista) {
                            "Alta Cocina" -> {textViewdescripcion.text = listaonclick.get(position).altacocina_desc
                                textViewplatos.text =listaonclick.get(position).altacocina_platos}
                            "Cocina Tradicional" -> {textViewdescripcion.text = listaonclick.get(position).cocinatradicional_desc
                                textViewplatos.text =listaonclick.get(position).cocinatradicional_platos}
                            "Cocina Lowcost" -> {textViewdescripcion.text = listaonclick.get(position).cocinalowcost_desc
                                textViewplatos.text =listaonclick.get(position).cocinalowcost_platos}
                        }
                        linearcantidadde.setVisibility(View.VISIBLE)
                        textViewncantidaddetit.text = "Nº de personas"
                    } else {
                        linneardescripcion.setVisibility(View.GONE)
                        linnearplatos.setVisibility(View.GONE)
                        if (categoria_lista.contains("Piscina")){
                            linearcantidadde.setVisibility(View.GONE)
                            textViewpreciofinal.text = precioporitem.toString() + "€"
                            preciofinal=Integer. parseInt(precioporitem.toString())
                        } else{
                            linearcantidadde.setVisibility(View.VISIBLE)
                            when(categoria_lista){
                                "Limpiador" -> textViewncantidaddetit.text = "Número de horas"
                                "Cortacesped" -> textViewncantidaddetit.text = "Número de m²"
                            }
                        }
                    }
                    editTextcantidadde.addTextChangedListener {
                        if (editTextcantidadde.text.isNotEmpty()){
                            preciofinal = Integer. parseInt(editTextcantidadde.text.toString())*precioporitem
                            textViewpreciofinal.text = preciofinal.toString() + "€"
                        }
                    }
                    buttonrealizarpedido.setOnClickListener {
                        if ((editTextcantidadde.text.isNotEmpty()||categoria_lista.contains("Piscina"))&&editTextfecha.text.isNotEmpty()&&editTexthorainicio.text.isNotEmpty()){
                            var fechasinbarras =editTextfecha.text.toString().replace("/","-").replace(" ","")
                            var idpedido = currentemailuser + "#"+ listaonclick.get(position).email +"#"+fechasinbarras+"#"+editTexthorainicio.text.toString()
                            var pedido = hashMapOf(
                                "email_cliente" to currentemailuser,
                                "email_trabajador" to listaonclick.get(position).email,
                                "tipo" to categoria_lista,
                                "precio" to preciofinal,
                                "cantidad" to editTextcantidadde.text.toString(),
                                "fecha" to editTextfecha.text.toString(),
                                "hora_inicio" to editTexthorainicio.text.toString(),
                                "puntuacion" to "",
                                "estado" to "Pendiente",
                                "imageurl_trabajador" to listaonclick.get(position).imageurl,
                                "nombreyapellido_trabajdor" to listaonclick.get(position).nombreyapellido,
                                "nombreyapellido_cliente" to nombreyapellido_user,
                                "direccion_cliente" to direccion_user)
                            db.collection("pedidos")
                                .document(idpedido)
                                .set(pedido)
                                .addOnSuccessListener {
                                    // reloading user_pedidos_fragment
                                    var frrr = getParentFragmentManager().getFragments().get(0)
                                    getParentFragmentManager().beginTransaction().remove(frrr).commit()

                                    mensajepopup("Pedido Realizado","El pedido se realizo con exito puede comprobar su estado en pedidos")
                                    closemenus()
                                    listview.adapter = null
                                    scrollseleccion.setVisibility(View.VISIBLE)
                                    textselecciona.setVisibility(View.VISIBLE)
                                    editTextcantidadde.setText("")
                                    editTextfecha.setText("")
                                    editTexthorainicio.setText("")
                                    textViewpreciofinal.text = "0€"
                                    scrolllist.setVisibility(View.GONE)
                                    scrollrealizarpedido.setVisibility(View.GONE)
                                }.addOnFailureListener {
                                    mensajepopup("Error al realizar pedido","Ocurrio un error al relizar el pedido compruebe los campos y pruebe otra vez")
                                }
                        } else{
                            Toast.makeText(context,"Error alguno de los campos esta vacio",Toast.LENGTH_LONG).show()
                        }
                    }
                }

            }
        }
        buttonaltacocina.setOnClickListener{
            getListratrabajadoresfiltrada("altacocina_precio","Alta Cocina")
            gotolista()}
        buttoncocinatradicional.setOnClickListener {
            getListratrabajadoresfiltrada("cocinatradicional_precio","Cocina Tradicional")
            gotolista()}
        buttoncocinalowcost.setOnClickListener {
            getListratrabajadoresfiltrada("cocinalowcost_precio","Cocina Lowcost")
            gotolista()}
        buttonpiscinagrande.setOnClickListener {
            getListratrabajadoresfiltrada("mantenimiento_precio_grande","Piscina Grande")
            gotolista()}
        buttonpiscinasmediana.setOnClickListener {
            getListratrabajadoresfiltrada("mantenimiento_precio_mediana","Piscina Mediana")
            gotolista()}
        buttonpiscinaspequenas.setOnClickListener {
            getListratrabajadoresfiltrada("mantenimiento_precio_pequena","Piscina Pequeña")
            gotolista()}
        buttonlimpiador.setOnClickListener {
            getListratrabajadoresfiltrada("limpiador_precio","Limpiador")
            gotolista()}
        buttoncortacesped.setOnClickListener {
            getListratrabajadoresfiltrada("cortacesped_precio","Cortacesped")
            gotolista()}
        imageButtonbackarrow.setOnClickListener {
            closemenus()
            listview.adapter = null
            scrollseleccion.setVisibility(View.VISIBLE)
            textselecciona.setVisibility(View.VISIBLE)
            scrolllist.setVisibility(View.GONE) }

        fun showDatePickerDialog() {
            val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
                val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
                editTextfecha.setText(selectedDate)
            })
            getActivity()?.let { newFragment.show(it.getSupportFragmentManager(), "datePicker") }
        }
        editTextfecha.setOnClickListener { showDatePickerDialog() }

        fun getTime(editText: EditText, context: Context){
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                var finalminute = minute.toString()
                if (minute.toString().length==1){
                    finalminute="0"+minute
                }
                val selectedtime = hour.toString() + ":" + finalminute
                editText.setText(selectedtime) }
            TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(
                Calendar.MINUTE), true).show()
        }
        editTexthorainicio.setOnClickListener {
            context?.let { it1 -> getTime(editTexthorainicio, it1) }
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