package com.example.wipehouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Trabajador_pedidos.newInstance] factory method to
 * create an instance of this fragment.
 */
class Trabajador_pedidos : Fragment() {
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
        // Inflate the layout for this fragment
        var vista = inflater.inflate(R.layout.fragment_trabajador_pedidos, container, false)
        var listviewpedidos = vista.findViewById<ListView>(R.id.listviewpedidos)
        var db = Firebase.firestore
        var currentuseremail = FirebaseAuth.getInstance().currentUser?.email
        var listpedidos = ArrayList<Pedido>()
        var imageViewlogoloading = vista.findViewById<ImageView>(R.id.imageViewlogoloading)
        var relativeloading = vista.findViewById<RelativeLayout>(R.id.relativeloading)
        var textViewisempty = vista.findViewById<TextView>(R.id.textViewisempty)
        Glide.with(this).load(R.drawable.loading_logo).into(imageViewlogoloading)
        db.collection("pedidos").whereEqualTo("email_trabajador",currentuseremail).get().addOnSuccessListener { result ->
            for (document in result) {
                var email_cliente = document.data["email_cliente"].toString()
                var email_trabajador = document.data["email_trabajador"].toString()
                var tipo = document.data["tipo"].toString()
                var precio = document.data["precio"].toString()
                var cantidad = document.data["cantidad"].toString()
                var fecha = document.data["fecha"].toString()
                var hora_inicio = document.data["hora_inicio"].toString()
                var puntuacion = document.data["puntuacion"].toString()
                var estado = document.data["estado"].toString()
                var imageurl_trabajador = document.data["imageurl_trabajador"].toString()
                var nombreyapellido_trabajdor = document.data["nombreyapellido_trabajdor"].toString()
                var nombreyapellido_cliente = document.data["nombreyapellido_cliente"].toString()
                var direccion_cliente = document.data["direccion_cliente"].toString()
                // --[Actualización del estado del peddo segun la fecha actual]--
                val sdf = SimpleDateFormat("d/M/y")
                val currentDate = sdf.format(Date())
                var time_pedido_split = hora_inicio.split(":")
                var currenttime_all =  Calendar.getInstance().time
                var currenttime_all_split = currenttime_all.toString().split(" ")
                var split_hours_minutes = currenttime_all_split[3].split(":")
                var currenthours = split_hours_minutes[0]
                var currentminute = split_hours_minutes[1]
                var currentemailuser = FirebaseAuth.getInstance().currentUser?.email
                var fechasinespacios =fecha.replace(" ","")
                val sdf2 = SimpleDateFormat("dd/MM/yyyy")
                val fecha_pedido = sdf2.parse(fechasinespacios)
                val fecha_actual = sdf2.parse(currentDate)
                if (estado.equals("Aceptado")&&(fecha_actual.after(fecha_pedido) || (fecha.replace(" ","")==currentDate&&(currenthours>time_pedido_split[0]||(currenthours==time_pedido_split[0]&&currentminute>time_pedido_split[1]))))){
                    var fechasinbarras =fecha.replace("/","-").replace(" ","")
                    var id_pedido = currentemailuser + "#"+ email_trabajador +"#"+fechasinbarras+"#"+hora_inicio
                    db.collection("pedidos").document(id_pedido).update("estado","Realizado")
                    estado = "Realizado"
                }
                if (estado.equals("Pendiente")&&(fecha_actual.after(fecha_pedido) || (fecha.replace(" ","")==currentDate&&(currenthours>time_pedido_split[0]||(currenthours==time_pedido_split[0]&&currentminute>time_pedido_split[1]))))){
                    var fechasinbarras =fecha.replace("/","-").replace(" ","")
                    var id_pedido = currentemailuser + "#"+ email_trabajador +"#"+fechasinbarras+"#"+hora_inicio
                    db.collection("pedidos").document(id_pedido).update("estado","Caducado")
                    estado = "Caducado"
                }
                // --[END Actualización del estado del peddo segun la fecha actual]--
                var currentpedido = Pedido(email_cliente,email_trabajador,tipo,precio, cantidad, fecha, hora_inicio, puntuacion, estado,imageurl_trabajador,nombreyapellido_trabajdor,nombreyapellido_cliente,direccion_cliente)
                listpedidos.add(currentpedido)
            }
            listviewpedidos.adapter = context?.let { PedidosTrabajadorArrayAdapter(it,R.layout.item_list_pedidos_trabajador,listpedidos) }
            relativeloading.setVisibility(View.GONE)
            listviewpedidos.setVisibility(View.VISIBLE)
            listviewpedidos.setEmptyView(textViewisempty)
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
         * @return A new instance of fragment Trabajador_pedidos.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Trabajador_pedidos().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}