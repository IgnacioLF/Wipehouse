package com.example.wipehouse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage



class test : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        var email = FirebaseAuth.getInstance().currentUser?.email
        var image = findViewById<ImageView>(R.id.imageView)
        var storage = Firebase.storage
        val storageRef = storage.reference
        val pathReference = storageRef.child("Trabajadores/"+email+".jpg")

        pathReference.downloadUrl.addOnSuccessListener {
            Glide.with(this ).load(it).into(image)
        }

    }
}
