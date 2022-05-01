package com.example.wipehouse

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class PedidosUserArrayAdapter (context : Context, viewtopaint : Int, private val pedidoslist : ArrayList<Pedido>): ArrayAdapter<Pedido>(context,viewtopaint,pedidoslist){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val currentlistitem = inflater.inflate(R.layout.item_list_pedidos_user, null)
        val textViewprecio = currentlistitem.findViewById<TextView>(R.id.textViewprecio)
        val buttonvalorar = currentlistitem.findViewById<Button>(R.id.buttonvalorar)
        val textViewfecha = currentlistitem.findViewById<TextView>(R.id.textViewfecha)
        val textViewcantidade = currentlistitem.findViewById<TextView>(R.id.textViewcantidade)
        val textViewNombreyapellido = currentlistitem.findViewById<TextView>(R.id.textViewNombreyapellido)
        val textViewEstado = currentlistitem.findViewById<TextView>(R.id.textViewEstado)
        val imageView_trabajador = currentlistitem.findViewById<ImageView>(R.id.imageView_trabajador)
        val options = RequestOptions().circleCrop()
        Glide.with(currentlistitem).load(pedidoslist.get(position).imageurl_trabajador).apply(options).dontAnimate().into(imageView_trabajador)
        textViewEstado.text = pedidoslist.get(position).estado
        textViewfecha.text = pedidoslist.get(position).fecha
        textViewprecio.text = pedidoslist.get(position).precio + "€"
        textViewNombreyapellido.text = pedidoslist.get(position).nombreyapellido_trabajdor
        when(pedidoslist.get(position).estado){
            "Pendiente" -> textViewEstado.setTextColor(Color.parseColor("#ce6b03"))
            "Aceptado" -> textViewEstado.setTextColor(Color.parseColor("#70a83b"))
            "Rechazado" -> textViewEstado.setTextColor(Color.parseColor("#bf0811"))
            "Realizado" -> textViewEstado.setTextColor(Color.parseColor("#006b33"))
        }
        when (pedidoslist.get(position).tipo){
            "Alta Cocina" -> textViewcantidade.text = pedidoslist.get(position).cantidad + " Personas"
            "Cocina Tradicional" -> textViewcantidade.text = pedidoslist.get(position).cantidad + " Personas"
            "Cocina Lowcost" -> textViewcantidade.text = pedidoslist.get(position).cantidad + " Personas"
            "Piscina Grande" -> textViewcantidade.text = "Pisciana Grande"
            "Piscina Mediana" -> textViewcantidade.text = "Piscina Mediana"
            "Piscina Pequeña" -> textViewcantidade.text = "Piscina Pequeña"
            "Limpiador" -> textViewcantidade.text = pedidoslist.get(position).cantidad + " Horas"
            "Cortacesped" -> textViewcantidade.text = pedidoslist.get(position).cantidad + " Metros²"
        }
        if (pedidoslist.get(position).estado.equals("Realizado")&&pedidoslist.get(position).puntuacion==""){
            buttonvalorar.setVisibility(View.VISIBLE)
        }
        buttonvalorar.setOnClickListener {
            textViewNombreyapellido.text = "valorado xd"
        }
        return currentlistitem
    }

}

