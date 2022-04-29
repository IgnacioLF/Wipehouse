package com.example.wipehouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [User_cuenta.newInstance] factory method to
 * create an instance of this fragment.
 */
class User_cuenta : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var editTextTextNombre : EditText
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
        var  view = inflater.inflate(R.layout.fragment_user_cuenta, container, false)
        var editTextTextNombre=view.findViewById<EditText>(R.id.editTextTextNombre)
        var editTextapellidos=view.findViewById<EditText>(R.id.editTextapellidos)
        var editTextTextContraseña=view.findViewById<EditText>(R.id.editTextTextContraseña)
        var editTextDNI=view.findViewById<EditText>(R.id.editTextDNI)
        var editTextEmail=view.findViewById<EditText>(R.id.editTextEmail)
        var editTextTelefono=view.findViewById<EditText>(R.id.editTextTelefono)
        var editTextDireccion=view.findViewById<EditText>(R.id.editTextDireccion)
        var editTextCodigopostal=view.findViewById<EditText>(R.id.editTextCodigopostal)
        var currentuseremail = ""
           currentuseremail= FirebaseAuth.getInstance().currentUser?.email.toString()
        var spinnerciudad = view.findViewById<Spinner>(R.id.spinnerciudad)
        var buttonModificar = view.findViewById<Button>(R.id.buttonModificar)
        var ciudadlista = arrayOf<String>("Madrid","Barcelona")
        var spinadapter = context?.let { ArrayAdapter(it,R.layout.myselectedspinner,ciudadlista) }
        if (spinadapter != null) {
            spinadapter.setDropDownViewResource(R.layout.mydropdownspinner)
        }
        spinnerciudad.adapter = spinadapter

        spinnerciudad.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        var db = Firebase.firestore
        db.collection("usuarios").document(currentuseremail).get().addOnSuccessListener { document ->
            var ciudad = document.data?.get("ciudad")?.toString()
            editTextTextNombre.setText(document.data?.get("nombre")?.toString())
            editTextapellidos.setText(document.data?.get("apellidos")?.toString())
            ciudad?.let { Usuario.Ciudades.valueOf(it).ordinal }?.let {
                spinnerciudad.setSelection(
                    it
                )
            }
            editTextCodigopostal.setText(document.data?.get("cp")?.toString())
            editTextDireccion.setText(document.data?.get("direccion")?.toString())
            editTextDNI.setText(document.data?.get("dni")?.toString())
            editTextTelefono.setText(document.data?.get("telefono")?.toString())
        }
        buttonModificar.setOnClickListener {
            // TODO modicar trabajos nombre y apellido y ciudad
            if (editTextTextNombre.text.isNotEmpty()&&editTextapellidos.text.isNotEmpty()&&editTextCodigopostal.text.isNotEmpty()&&editTextDireccion.text.isNotEmpty()&&editTextDNI.text.isNotEmpty()&&editTextTelefono.text.isNotEmpty()){
                val datauser = hashMapOf(
                    "nombre" to editTextTextNombre.text.toString(),
                    "apellidos" to editTextapellidos.text.toString(),
                    "ciudad" to spinnerciudad.selectedItem.toString(),
                    "direccion" to editTextDireccion.text.toString(),
                    "cp" to editTextCodigopostal.text.toString(),
                    "telefono" to editTextTelefono.text.toString(),
                    "dni" to editTextDNI.text.toString()
                )
                db.collection("usuarios").document(currentuseremail).set(datauser).addOnSuccessListener {
                    Toast.makeText(context,"Cambios guardados correctamente",Toast.LENGTH_LONG).show()
                }.addOnFailureListener {
                    Toast.makeText(context,"Error al realizar la operacion",Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context,"Error alguno de los campos esta vacio",Toast.LENGTH_LONG).show()
            }
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment User_cuenta.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            User_cuenta().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}


