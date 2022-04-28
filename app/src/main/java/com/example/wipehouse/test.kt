package com.example.wipehouse

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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





class test : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        var email = FirebaseAuth.getInstance().currentUser?.email
        var image = findViewById<ImageView>(R.id.imageViewlabuena)
        var storage = Firebase.storage
        val storageRef = storage.reference
        val pathReference = storageRef.child("Trabajadores/"+email+".jpg")

        val options = RequestOptions().circleCrop()
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/wipehouse-ffdd1.appspot.com/o/Trabajadores%2Flabuena%40gmail.com.jpg?alt=media&token=8235fbdc-1ae3-4ac1-9417-664e77289264").apply(options).dontAnimate().into(image)
    }
    
}
