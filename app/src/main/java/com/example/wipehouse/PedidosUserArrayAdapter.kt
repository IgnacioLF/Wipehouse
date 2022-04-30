package com.example.wipehouse

import android.content.Context
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
      //  Glide.with(currentlistitem).load(pedidoslist.get(position).imageurl).apply(options).dontAnimate().into(image)
        return currentlistitem
    }

}

