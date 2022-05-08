package com.example.wipehouse

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import nl.joery.animatedbottombar.AnimatedBottomBar

class User_MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_main)
        var ViewPager2 = findViewById<ViewPager2>(R.id.viewpager)
        var bottom_bar = findViewById<AnimatedBottomBar>(R.id.bottom_bar)
        val fragments: ArrayList<Fragment> = arrayListOf(
            User_pedidos(),
            User_buscar(),
            User_cuenta()
        )

        var ViewPagerAdapter = ViewPagerAdapter(fragments,this)

        ViewPager2.setAdapter(ViewPagerAdapter)

        ViewPager2.setCurrentItem(1);

        bottom_bar.setupWithViewPager2(ViewPager2)
       // ViewPager2.offscreenPageLimit= 0

        var myPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Toast.makeText(this@User_MainActivity, "Selected position: ${position}", Toast.LENGTH_SHORT).show()
                if(position==0){
                    var frrr = getSupportFragmentManager().getFragments().get(0)
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.remove(frrr)
                    fragmentTransaction.commit()
                }
                if (position==1){
                    var frrr = getSupportFragmentManager().getFragments().get(0)
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.remove(frrr)
                    fragmentTransaction.commit()
                }
                if (position==2){
                    var frrr = getSupportFragmentManager().getFragments().get(0)
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.remove(frrr)
                    fragmentTransaction.commit()
                }
            }
        }

        ViewPager2.registerOnPageChangeCallback(myPageChangeCallback)


    /*    if (ViewPager2.currentItem==1){
            getSupportFragmentManager()
                .beginTransaction()
                .detach(User_pedidos())
                .attach(User_pedidos())
                .commit();
            Log.d("ento en el if:", "si--------------------------")
        }
*/
    }
}