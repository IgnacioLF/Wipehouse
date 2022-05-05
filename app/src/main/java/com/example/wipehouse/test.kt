package com.example.wipehouse

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.System.load
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.ktx.firestore
import java.text.SimpleDateFormat
import java.util.*


class test : AppCompatActivity() {

    lateinit var etPlannedDate : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        Log.d("errorimagen:","aqaaaaa")
        var email = FirebaseAuth.getInstance().currentUser?.email
        var image = findViewById<ImageView>(R.id.imageViewlabuena)
        var storage = Firebase.storage
        val storageRef = storage.reference
        val pathReference = storageRef.child("Trabajadores/"+email+".jpg")
        var db = Firebase.firestore
        var urlimagetrabajador = ""
        var imageViewloading = findViewById<ImageView>(R.id.imageViewloading)
        Glide.with(this).load(R.drawable.loading_logo).into(imageViewloading)
        db.collection("trabajadores").document("cocinero2@gmail.com").get().addOnSuccessListener {result ->
            urlimagetrabajador= result.data?.get("imageurl").toString()

        }.addOnFailureListener {
            Log.d("errorimagen:",it.toString())
        }

        Log.d("errorimagen:",urlimagetrabajador + " algooo")
        Glide.with(this).load(urlimagetrabajador).dontAnimate().into(image)

        val options = RequestOptions().circleCrop()
       // Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/wipehouse-ffdd1.appspot.com/o/Trabajadores%2Flabuena%40gmail.com.jpg?alt=media&token=8235fbdc-1ae3-4ac1-9417-664e77289264").apply(options).dontAnimate().into(image)
    }


    fun getTime(editText: EditText, context: Context){
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            var finalminute = minute.toString()
            if (minute.toString().length==1){
                finalminute="0"+minute
            }
            val selectedtime = hour.toString() + ":" + finalminute
            editText.setText(selectedtime) }
        TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
    }
    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            etPlannedDate.setText(selectedDate)
        })

        newFragment.show(supportFragmentManager, "datePicker")
    }
}
