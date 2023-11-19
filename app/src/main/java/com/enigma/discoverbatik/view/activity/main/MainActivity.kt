package com.enigma.discoverbatik.view.activity.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.databinding.ActivityMainBinding
import com.enigma.discoverbatik.view.fragment.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val fragmentManager = supportFragmentManager
        val homeFragment = HomeFragment()
        val fragment = fragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)

        if (fragment !is HomeFragment) {
            Log.d("My Flexible Fragment", "Fragment Name: " + HomeFragment::class.java.simpleName)
            fragmentManager
                .beginTransaction()
                .add(R.id.content, homeFragment, HomeFragment::class.java.simpleName)
                .commit()
        }


    }
}