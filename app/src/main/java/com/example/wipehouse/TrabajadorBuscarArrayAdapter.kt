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
        val imageViewestrella1 = currentlistitem.findViewById<ImageView>(R.id.imageViewestrella1)
        val imageViewestrella2 = currentlistitem.findViewById<ImageView>(R.id.imageViewestrella2)
        val imageViewestrella3 = currentlistitem.findViewById<ImageView>(R.id.imageViewestrella3)
        val imageViewestrella4 = currentlistitem.findViewById<ImageView>(R.id.imageViewestrella4)
        val imageViewestrella5 = currentlistitem.findViewById<ImageView>(R.id.imageViewestrella5)
        val options = RequestOptions().circleCrop()
        Glide.with(currentlistitem).load(trabajadorlist.get(position).imageurl).apply(options).dontAnimate().into(image)
        textViewNombreyapellido.text = trabajadorlist.get(position).nombreyapellido
        textViewCategoria.text = categoria
        when (categoria) {
            "Alta Cocina" -> textViewprecio.text = trabajadorlist.get(position).altacocina_precio + "€"
            "Cocina Tradicional" -> textViewprecio.text = trabajadorlist.get(position).cocinatradicional_precio + "€"
            "Cocina Lowcost" -> textViewprecio.text = trabajadorlist.get(position).cocinalowcost_precio + "€"
            "Piscina Grande" -> textViewprecio.text = trabajadorlist.get(position).mantenimiento_precio_grande + "€"
            "Piscina Mediana" -> textViewprecio.text = trabajadorlist.get(position).mantenimiento_precio_mediana + "€"
            "Piscina Pequeña" -> textViewprecio.text = trabajadorlist.get(position).mantenimiento_precio_pequena + "€"
            "Limpiador" -> textViewprecio.text = trabajadorlist.get(position).limpiador_precio + "€"
            "Cortacesped" -> textViewprecio.text = trabajadorlist.get(position).cortacesped_precio + "€"
        }
        when (trabajadorlist.get(position).puntucaion_media.toInt()) {
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
        return currentlistitem
    }

}

