package com.enigma.discoverbatik.view.activity.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.databinding.ActivityMainBinding
import com.enigma.discoverbatik.view.fragment.explore.ExploreFragment
import com.enigma.discoverbatik.view.fragment.favorite.FavoriteFragment
import com.enigma.discoverbatik.view.fragment.home.HomeFragment
import com.enigma.discoverbatik.view.fragment.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    private fun fragmentManager(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.content, fragment, fragment.javaClass.simpleName)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.apply {
            bottomNavigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home -> {
                        val fragment = HomeFragment.newInstance()
                        fragmentManager(fragment)
                    }

                    R.id.explore -> {
                        val fragment = ExploreFragment.newInstance()
                        fragmentManager(fragment)
                    }

                    R.id.favorite -> {
                        val fragment = FavoriteFragment.newInstance()
                        fragmentManager(fragment)
                    }

                    R.id.profile -> {
                        val fragment = ProfileFragment.newInstance()
                        fragmentManager(fragment)
                    }
                }
                bottomNavigation.menu.findItem(item.itemId)?.isChecked = true
                false
            }
        }

        val fragment = HomeFragment.newInstance()
        fragmentManager(fragment)

    }
}
