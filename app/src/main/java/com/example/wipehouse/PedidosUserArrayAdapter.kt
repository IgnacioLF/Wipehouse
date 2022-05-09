package com.example.wipehouse

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.content.DialogInterface
import android.view.Gravity
import android.widget.*
import android.widget.RatingBar.OnRatingBarChangeListener
import kotlin.collections.ArrayList


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
        val textViewtipo = currentlistitem.findViewById<TextView>(R.id.textViewtipo)
        val options_circleimage = RequestOptions().circleCrop()
        var db = Firebase.firestore

        Glide.with(currentlistitem).load(pedidoslist.get(position).imageurl_trabajador).apply(options_circleimage).dontAnimate().into(imageView_trabajador)
        textViewtipo.text = pedidoslist.get(position).tipo
        textViewEstado.text = pedidoslist.get(position).estado
        textViewfecha.text = pedidoslist.get(position).fecha
        textViewprecio.text = pedidoslist.get(position).precio + "€"
        textViewNombreyapellido.text = pedidoslist.get(position).nombreyapellido_trabajdor
        when(pedidoslist.get(position).estado){
            "Pendiente" -> textViewEstado.setTextColor(Color.parseColor("#ce6b03"))
            "Aceptado" -> textViewEstado.setTextColor(Color.parseColor("#70a83b"))
            "Rechazado" -> textViewEstado.setTextColor(Color.parseColor("#bf0811"))
            "Realizado" -> textViewEstado.setTextColor(Color.parseColor("#006b33"))
            "Caducado" -> textViewEstado.setTextColor(Color.parseColor("#A5316B"))
        }
        when (pedidoslist.get(position).tipo){
            "Alta Cocina" -> textViewcantidade.text = pedidoslist.get(position).cantidad + " Personas"
            "Cocina Tradicional" -> textViewcantidade.text = pedidoslist.get(position).cantidad + " Personas"
            "Cocina Lowcost" -> textViewcantidade.text = pedidoslist.get(position).cantidad + " Personas"
            "Piscina Grande" -> {textViewcantidade.text = "Pisciana Grande"
                textViewtipo.text = "Mantenimiento de piscinas"}
            "Piscina Mediana" -> {textViewcantidade.text = "Piscina Mediana"
                textViewtipo.text = "Mantenimiento de piscinas"}
            "Piscina Pequeña" -> {textViewcantidade.text = "Piscina Pequeña"
                textViewtipo.text = "Mantenimiento de piscinas"}
            "Limpiador" -> textViewcantidade.text = pedidoslist.get(position).cantidad + " Horas"
            "Cortacesped" -> textViewcantidade.text = pedidoslist.get(position).cantidad + " Metros²"
        }
        if (pedidoslist.get(position).estado.equals("Realizado")&&pedidoslist.get(position).puntuacion==""){
            buttonvalorar.setVisibility(View.VISIBLE)
        }
        buttonvalorar.setOnClickListener {
            val popDialog: AlertDialog.Builder = AlertDialog.Builder(context)
            val linearLayout = LinearLayout(context)
            val rating = RatingBar(context)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            rating.layoutParams = lp
            rating.numStars = 5
            rating.stepSize = 1f
            linearLayout.addView(rating)
            linearLayout.gravity= Gravity.CENTER
            popDialog.setTitle("Valora el trabajo realizado")
            popDialog.setView(linearLayout)

            rating.onRatingBarChangeListener =
                OnRatingBarChangeListener { ratingBar, v, b -> println("Rated val:$v") }
            popDialog.setPositiveButton(android.R.string.ok,
                DialogInterface.OnClickListener { dialog, which ->
                    if (rating.progress.toString().equals("0")){
                        Toast.makeText(context,"Debe incluir alemnos 1 estrella en su valoración", Toast.LENGTH_LONG).show()
                    } else {
                        var fechasinbarras = pedidoslist.get(position).fecha.replace("/", "-").replace(" ","")
                        var idpedido =
                            pedidoslist.get(position).email_cliente + "#" + pedidoslist.get(position).email_trabajdor + "#" + fechasinbarras + "#" + pedidoslist.get(
                                position
                            ).hora_inicio
                        db.collection("pedidos").document(idpedido)
                            .update("puntuacion", rating.progress.toString()).addOnSuccessListener {
                                var contcantpedidos = 0
                                var contestrellatotal = 0
                                var puntuacionmedia = 0
                                db.collection("pedidos").whereEqualTo("email_trabajador",pedidoslist.get(position).email_trabajdor).get().addOnSuccessListener { result ->
                                    for (document in result){
                                        if (document.data["puntuacion"].toString().equals("")==false){
                                            contcantpedidos = contcantpedidos + 1
                                            contestrellatotal = contestrellatotal + Integer. parseInt(document.data["puntuacion"].toString())
                                        }
                                    }
                                    puntuacionmedia =  contestrellatotal /contcantpedidos
                                    db.collection("trabajadores").document(pedidoslist.get(position).email_trabajdor).update("puntuacion_media",puntuacionmedia)
                                    Toast.makeText(context, "Valoración realizada con exito", Toast.LENGTH_LONG).show()
                                }
                            dialog.dismiss()
                                pedidoslist.get(position).changePuntuacion(rating.progress.toString())
                            buttonvalorar.setVisibility(View.GONE)
                        }.addOnFailureListener {
                            Toast.makeText(context, "Error no se pudo realizar la operación", Toast.LENGTH_LONG).show()
                            dialog.cancel()
                        }
                    }
                }) // Button Cancel
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
            popDialog.create()
            popDialog.show()
        }
        return currentlistitem
    }

}

