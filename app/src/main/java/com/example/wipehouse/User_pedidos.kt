package com.example.wipehouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [User_pedidos.newInstance] factory method to
 * create an instance of this fragment.
 */
class User_pedidos : Fragment() {
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
        val vista = inflater.inflate(R.layout.fragment_user_pedidos, container, false)
        var listviewpedidos = vista.findViewById<ListView>(R.id.listviewpedidos)
        var db = Firebase.firestore
        var emailcurrentuser = FirebaseAuth.getInstance().currentUser?.email
        var listpedidos = ArrayList<Pedido>()

        db.collection("pedidos").whereEqualTo("email_cliente",emailcurrentuser).get().addOnSuccessListener { result ->
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
                var currentpedido = Pedido(email_cliente,email_trabajador,tipo,precio, cantidad, fecha, hora_inicio, puntuacion, estado,imageurl_trabajador,nombreyapellido_trabajdor)
                listpedidos.add(currentpedido)
            }
        }
        listviewpedidos.adapter = context?.let { PedidosUserArrayAdapter(it,R.layout.item_list_pedidos_user,listpedidos) }
        return vista
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment User_pedidos.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            User_pedidos().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}