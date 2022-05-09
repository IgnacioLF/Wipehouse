package com.example.wipehouse

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PedidosTrabajadorArrayAdapter (context : Context, viewtopaint : Int, private val pedidoslist : ArrayList<Pedido>): ArrayAdapter<Pedido>(context,viewtopaint,pedidoslist) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val currentlistitem = inflater.inflate(R.layout.item_list_pedidos_trabajador, null)
        val textViewprecio = currentlistitem.findViewById<TextView>(R.id.textViewprecio)
        val textViewfecha = currentlistitem.findViewById<TextView>(R.id.textViewfecha)
        val textViewcantidade = currentlistitem.findViewById<TextView>(R.id.textViewcantidade)
        val textviewdireccion = currentlistitem.findViewById<TextView>(R.id.textviewdireccion)
        val textViewNombreyapellido = currentlistitem.findViewById<TextView>(R.id.textViewNombreyapellido)
        val buttonrechazar = currentlistitem.findViewById<Button>(R.id.buttonrechazar)
        val buttonaceptar = currentlistitem.findViewById<Button>(R.id.buttonaceptar)
        val textViewEstado = currentlistitem.findViewById<TextView>(R.id.textViewEstado)
        val textViewcategoria = currentlistitem.findViewById<TextView>(R.id.textViewcategoria)
        val imageViewlogocategoria = currentlistitem.findViewById<ImageView>(R.id.imageViewlogocategoria)
        var db = Firebase.firestore

        textViewprecio.text = pedidoslist.get(position).precio + "€"
        textViewfecha.text = pedidoslist.get(position).fecha
        textviewdireccion.text = pedidoslist.get(position).direccion_cliente
        textViewNombreyapellido.text = pedidoslist.get(position).nombreyapellido_cliente
        textViewEstado.text = pedidoslist.get(position).estado
        textViewcategoria.text = pedidoslist.get(position).tipo
        when(pedidoslist.get(position).estado){
            "Pendiente" -> textViewEstado.setTextColor(Color.parseColor("#ce6b03"))
            "Aceptado" -> textViewEstado.setTextColor(Color.parseColor("#70a83b"))
            "Rechazado" -> textViewEstado.setTextColor(Color.parseColor("#bf0811"))
            "Realizado" -> textViewEstado.setTextColor(Color.parseColor("#006b33"))
            "Caducado" -> textViewEstado.setTextColor(Color.parseColor("#A5316B"))
        }
        when (pedidoslist.get(position).tipo){
            "Alta Cocina" -> {textViewcantidade.text = pedidoslist.get(position).cantidad + " Personas"
                imageViewlogocategoria.setImageResource(R.drawable.cocinero_icon)}
            "Cocina Tradicional" -> {textViewcantidade.text = pedidoslist.get(position).cantidad + " Personas"
                imageViewlogocategoria.setImageResource(R.drawable.cocinero_icon)}
            "Cocina Lowcost" -> {textViewcantidade.text = pedidoslist.get(position).cantidad + " Personas"
                imageViewlogocategoria.setImageResource(R.drawable.cocinero_icon)}
            "Piscina Grande" -> {textViewcantidade.text = "Pisciana Grande"
                textViewcategoria.text = "Mantenimiento\n de piscinas"
                imageViewlogocategoria.setImageResource(R.drawable.mantenimiento_icon)}
            "Piscina Mediana" -> {textViewcantidade.text = "Piscina Mediana"
                textViewcategoria.text = "Mantenimiento\n de piscinas"
                imageViewlogocategoria.setImageResource(R.drawable.mantenimiento_icon)}
            "Piscina Pequeña" -> {textViewcantidade.text = "Piscina Pequeña"
                textViewcategoria.text = "Mantenimiento\n de piscinas"
                imageViewlogocategoria.setImageResource(R.drawable.mantenimiento_icon)}
            "Limpiador" -> {textViewcantidade.text = pedidoslist.get(position).cantidad + " Horas"
                imageViewlogocategoria.setImageResource(R.drawable.limpiador_icon)}
            "Cortacesped" -> {textViewcantidade.text = pedidoslist.get(position).cantidad + " Metros²"
                imageViewlogocategoria.setImageResource(R.drawable.cortacesped_icon)}
        }
        if (pedidoslist.get(position).estado.equals("Pendiente")){
            buttonrechazar.setVisibility(View.VISIBLE)
            buttonaceptar.setVisibility(View.VISIBLE)
        }
        buttonaceptar.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("¿Quieres aceptar el pedido?")
            builder.setMessage("Pedido realizado por : " + pedidoslist.get(position).nombreyapellido_cliente + "\n"
                    + "Tipo : " + pedidoslist.get(position).tipo + "\n"
                    + "Fecha : " + pedidoslist.get(position).fecha +"\n"
                    + "Direccion : " + pedidoslist.get(position).direccion_cliente + "\n"
                    + "Precio : " + pedidoslist.get(position).precio + "€\n")
            builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialogInterface, i ->
                db.collection("pedidos").whereEqualTo("email_trabajador",pedidoslist.get(position).email_trabajdor).get().addOnSuccessListener { result ->
                    for (document in result){
                        if (document.data["email_cliente"].toString().equals(pedidoslist.get(position).email_cliente)&&
                                document.data["fecha"].toString().equals(pedidoslist.get(position).fecha)&&
                                document.data["hora_inicio"].toString().equals(pedidoslist.get(position).hora_inicio)){
                            db.collection("pedidos").document(document.id).update("estado","Aceptado").addOnSuccessListener {
                                buttonrechazar.setVisibility(View.GONE)
                                buttonaceptar.setVisibility(View.GONE)
                                pedidoslist.get(position).changeEstado("Aceptado")
                                textViewEstado.text = "Aceptado"
                                textViewEstado.setTextColor(Color.parseColor("#70a83b"))
                            }.addOnFailureListener {
                                Toast.makeText(context,"Error al realizar la operacion",Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            })
            builder.setNegativeButton("Cancelar",null)
            val dialog = builder.create()
            dialog.show()
        }
        buttonrechazar.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("¿Quieres rechazar el pedido?")
            builder.setMessage("Pedido realizado por : " + pedidoslist.get(position).nombreyapellido_cliente + "\n"
                    + "Tipo : " + pedidoslist.get(position).tipo + "\n"
                    + "Fecha : " + pedidoslist.get(position).fecha +"\n"
                    + "Direccion : " + pedidoslist.get(position).direccion_cliente + "\n"
                    + "Precio : " + pedidoslist.get(position).precio + "€\n")
            builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialogInterface, i ->
                db.collection("pedidos").whereEqualTo("email_trabajador",pedidoslist.get(position).email_trabajdor).get().addOnSuccessListener { result ->
                    for (document in result){
                        if (document.data["email_cliente"].toString().equals(pedidoslist.get(position).email_cliente)&&
                            document.data["fecha"].toString().equals(pedidoslist.get(position).fecha)&&
                            document.data["hora_inicio"].toString().equals(pedidoslist.get(position).hora_inicio)){
                            db.collection("pedidos").document(document.id).update("estado","Rechazado").addOnSuccessListener {
                                buttonrechazar.setVisibility(View.GONE)
                                buttonaceptar.setVisibility(View.GONE)
                                pedidoslist.get(position).changeEstado("Rechazado")
                                textViewEstado.text = "Rechazado"
                                textViewEstado.setTextColor(Color.parseColor("#bf0811"))
                            }.addOnFailureListener {
                                Toast.makeText(context,"Error al realizar la operacion",Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            })
            builder.setNegativeButton("Cancelar",null)
            val dialog = builder.create()
            dialog.show()
        }
        return currentlistitem
    }
}