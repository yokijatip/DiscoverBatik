package com.enigma.discoverbatik.view.activity.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.enigma.discoverbatik.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    private lateinit var cartBinding: ActivityCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartBinding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(cartBinding.root)

        cartBinding.btnBack.setOnClickListener {
            finish()
        }


    }
}