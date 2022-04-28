package com.example.wipehouse

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TrabajadorBuscarArrayAdapter (context : Context, viewtopaint : Int, private val trabajadorlist : ArrayList<Trabajador>, var categoria:String): ArrayAdapter<Trabajador>(context,viewtopaint,trabajadorlist){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val currentlistitem = inflater.inflate(R.layout.item_list_trabajador_buscar, null)
        val textViewNombreyapellido = currentlistitem.findViewById<TextView>(R.id.textViewNombreyapellido)
        val textViewCategoria = currentlistitem.findViewById<TextView>(R.id.textViewCategoria)
        val textViewprecio = currentlistitem.findViewById<TextView>(R.id.textViewprecio)
        val image = currentlistitem.findViewById<ImageView>(R.id.imageView_trabajador)
        val options = RequestOptions().circleCrop()
        Log.d("imageurlpara ver :",trabajadorlist.get(position).imageurl)
        Glide.with(currentlistitem).load(trabajadorlist.get(position).imageurl).apply(options).dontAnimate().into(image)
        textViewNombreyapellido.text = trabajadorlist.get(position).nombreyapellido
        textViewCategoria.text = categoria
        when (categoria) {
            "Alta Cocina" -> textViewprecio.text = trabajadorlist.get(position).altacocina_precio + "€"
            "Cocina Tradicional" -> textViewprecio.text = trabajadorlist.get(position).cocinatradicional_precio + "€"
            "Cocina Lowcost" -> textViewprecio.text = trabajadorlist.get(position).cocinalowcost_precio + "€"
        }
        return currentlistitem
    }

}