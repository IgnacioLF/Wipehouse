package com.example.wipehouse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import nl.joery.animatedbottombar.AnimatedBottomBar

class Trabajador_MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trabajador_main)
        var ViewPager2 = findViewById<ViewPager2>(R.id.viewpager)
        var bottom_bar = findViewById<AnimatedBottomBar>(R.id.bottom_bar)
        val fragments: ArrayList<Fragment> = arrayListOf(
            Trabajador_aceptados(),
            Trabajador_pedidos(),
            Trabajador_cuenta()
        )
        var ViewPagerAdapter = ViewPagerAdapter(fragments,this)
        ViewPager2.setAdapter(ViewPagerAdapter)
        ViewPager2.setCurrentItem(1);
        bottom_bar.setupWithViewPager2(ViewPager2)
    }
}