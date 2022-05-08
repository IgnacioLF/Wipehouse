package com.example.wipehouse

class Pedido (var email_cliente:String,
              var email_trabajdor:String,
              var tipo:String,
              var precio:String,
              var cantidad:String,
              var fecha:String,
              var hora_inicio:String,
              var puntuacion:String,
              var estado:String,
              var imageurl_trabajador:String,
              var nombreyapellido_trabajdor:String,
              var nombreyapellido_cliente :String,
              var direccion_cliente :String) {

    fun changeEstado(newestado: String){
        this.estado = newestado
    }

    fun changePuntuacion(newpuntuacion :String){
        this.puntuacion = newpuntuacion
    }
}