package com.example.wipehouse

class Usuario (var email :String,var nombre:String,var apellidos:String,var ciudad:Ciudades,var direccion:String,var cp:String,var telefono:String,var dni:String) {
    enum class Ciudades{
        Madrid,Barcelona
    }
}