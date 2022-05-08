package com.example.wipehouse

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

object FragmentUtil {

    fun refreshFragment(context: Context?) {
        context?.let {
            val fragmentManager = (context as? AppCompatActivity)?.supportFragmentManager
            fragmentManager?.let {
                val currentFragment = fragmentManager.findFragmentById(R.id.user_buscar)
                currentFragment?.let{
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.detach(it)
                    fragmentTransaction.attach(it)
                    fragmentTransaction.commit()
                }
            }
        }
    }
}