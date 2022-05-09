package com.example.wipehouse

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.regex.Pattern

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Trabajador_cuenta.newInstance] factory method to
 * create an instance of this fragment.
 */
class Trabajador_cuenta : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var imageUri : Uri

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
        var vista = inflater.inflate(R.layout.fragment_trabajador_cuenta, container, false)
        var db = Firebase.firestore
        var imageurltrabajador = ""
        var newimageurl = ""
        val options = RequestOptions().circleCrop()
        var imageButtonAddimage = vista.findViewById<ImageButton>(R.id.imageButtonAddimage)
        var buttonEditartrabajos = vista.findViewById<Button>(R.id.buttonEditartrabajos)
        var imageViewtrabajadorfoto = vista.findViewById<ImageView>(R.id.imageViewtrabajadorfoto)
        var editTextTextNombre=vista.findViewById<EditText>(R.id.editTextTextNombre)
        var editTextapellidos=vista.findViewById<EditText>(R.id.editTextapellidos)
        var editTextDNI=vista.findViewById<EditText>(R.id.editTextDNI)
        var editTextTelefono=vista.findViewById<EditText>(R.id.editTextTelefono)
        var editTextDireccion=vista.findViewById<EditText>(R.id.editTextDireccion)
        var editTextCodigopostal=vista.findViewById<EditText>(R.id.editTextCodigopostal)
        var currentuseremail = ""
        currentuseremail= FirebaseAuth.getInstance().currentUser?.email.toString()
        var spinnerciudad = vista.findViewById<Spinner>(R.id.spinnerciudad)
        var buttonModificar = vista.findViewById<Button>(R.id.buttonModificar)
        var buttonLogout = vista.findViewById<Button>(R.id.buttonLogout)
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
        db.collection("trabajadores").document(currentuseremail).get().addOnSuccessListener { document ->
            imageurltrabajador = document.data?.get("imageurl").toString()
            Glide.with(this).load(imageurltrabajador).apply(options).dontAnimate().into(imageViewtrabajadorfoto)
            imageViewtrabajadorfoto.setVisibility(View.VISIBLE)
        }
        buttonLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(context,MainActivity::class.java))
        }
        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == AppCompatActivity.RESULT_OK
                && result.data != null
            ) {
                imageUri = result.data!!.data!!
                Glide.with(this).load(imageUri).apply(options).dontAnimate().into(imageViewtrabajadorfoto)
            }
        }
        imageButtonAddimage.setOnClickListener {
            var openGalleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            launcher.launch(openGalleryIntent)
        }
        buttonModificar.setOnClickListener {
           //TODO revisar y añadir faileturelisener
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
                   if (this::imageUri.isInitialized) {
                       var storagereference = FirebaseStorage.getInstance().getReference()
                       var fileRef =
                           storagereference.child("Trabajadores/" + currentuseremail + ".jpg")
                       fileRef.putFile(imageUri).addOnSuccessListener {
                           var storage = Firebase.storage
                           val storageRef = storage.reference
                           val pathReference =
                               storageRef.child("Trabajadores/" + currentuseremail + ".jpg")
                           pathReference.downloadUrl.addOnSuccessListener {
                                newimageurl = it.toString()
                               var nombreyapellido = editTextTextNombre.text.toString() + " " + editTextapellidos.text.toString()
                               if (editTextapellidos.text.toString().contains(" ")) {
                                   var apellidos_split = Pattern.compile(" ").split(editTextapellidos.text.toString())
                                   nombreyapellido = editTextTextNombre.text.toString() + " " + apellidos_split[0]
                               }
                               db.collection("trabajadores").document(currentuseremail)
                                   .update("ciudad", spinnerciudad.selectedItem.toString(),
                                       "imageurl",newimageurl,
                                   "nombreyapellido",nombreyapellido)
                               db.collection("pedidos").whereEqualTo("email_trabajador",currentuseremail).get().addOnSuccessListener { result ->
                                   for (document in result){
                                       db.collection("pedidos").document(document.id).update(
                                           "imageurl_trabajador",newimageurl,
                                           "nombreyapellido_trabajador",nombreyapellido)
                                   }
                                   Toast.makeText(context,"Cambios guardados correctamente",Toast.LENGTH_LONG).show()
                               }
                           }.addOnFailureListener {
                               //TODO error
                           }
                       }
                   } else {
                       var nombreyapellido = editTextTextNombre.text.toString() + " " + editTextapellidos.text.toString()
                       if (editTextapellidos.text.toString().contains(" ")) {
                           var apellidos_split = Pattern.compile(" ").split(editTextapellidos.text.toString())
                           nombreyapellido = editTextTextNombre.text.toString() + " " + apellidos_split[0]
                       }
                       db.collection("trabajadores").document(currentuseremail)
                           .update("ciudad", spinnerciudad.selectedItem.toString(),
                               "nombreyapellido",nombreyapellido)
                       db.collection("pedidos").whereEqualTo("email_trabajador",currentuseremail).get().addOnSuccessListener { result ->
                           for (document in result){
                               db.collection("pedidos").document(document.id).update("nombreyapellido_trabajador",nombreyapellido)
                           }
                           Toast.makeText(context,"Cambios guardados correctamente",Toast.LENGTH_LONG).show()
                       }
                   }
                }.addOnFailureListener {
                    Toast.makeText(context,"Error al realizar la operación",Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context,"Error alguno de los campos esta vacio",Toast.LENGTH_LONG).show()
            }
        }
        buttonEditartrabajos.setOnClickListener {
            startActivity(Intent(context,Trabajos::class.java))
        }
        return vista
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Trabajador_cuenta.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Trabajador_cuenta().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}