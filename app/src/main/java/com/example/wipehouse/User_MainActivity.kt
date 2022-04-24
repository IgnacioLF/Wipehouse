package com.example.wipehouse

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

class User_MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_main)
        var ViewPager2 = findViewById<ViewPager2>(R.id.viewpager)
        var tab_layout =findViewById<TabLayout>(R.id.tablayout)
        val fragments: ArrayList<Fragment> = arrayListOf(
            User_pedidos(),
            User_buscar(),
            User_cuenta()
        )
        var ViewPagerAdapter = ViewPagerAdapter(fragments,this)
        ViewPager2.setAdapter(ViewPagerAdapter)
        TabLayoutMediator(tab_layout,ViewPager2){ tab,position ->
            if (position==0){
                tab.text ="Pedidos"
                tab.setIcon(R.drawable.pedidosicon_nocolor)
            } else if (position==1){
                tab.text ="Buscar"
            } else{
                tab.text ="Cuenta"
                tab.setCustomView(R.layout.customtab_user_cuenta)
            }
        }.attach()


       // ViewPager2.OnPageChangeCallback(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
     /*   ViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    // you are on the first page
                }
                else if (position == 1) {
                    // you are on the second page
                }
                else if (position == 2){
                    // you are on the third page
                    TabLayout.TabLayoutOnPageChangeListener(tab_layout)
                }
                super.onPageSelected(position)
            }
        })*/

     /*   tab_layout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
             /*   ViewPager2.currentItem = tab.position
                if (tab.position==2){
                    Log.d("llego¿:","esta por aqui")
                    tab.setCustomView(R.layout.customtab_user_cuenta_on_selected)
                }*/
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {


            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

        })
*/

    }
}