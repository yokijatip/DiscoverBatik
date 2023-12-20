package com.enigma.discoverbatik.view.activity.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enigma.discoverbatik.data.remote.response.BatikItem
import com.enigma.discoverbatik.databinding.ActivityCartBinding
import com.enigma.discoverbatik.models.adapter.cart.CartAdapter
import com.enigma.discoverbatik.utils.CommonUtils

class CartActivity : AppCompatActivity(), CartAdapter.OnItemClickListener {

    private lateinit var cartBinding: ActivityCartBinding
    private lateinit var cartManager: CartManager
    private lateinit var cartAdapter: CartAdapter
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartBinding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(cartBinding.root)

        cartManager = CartManager.getInstance()

        cartBinding.btnBack.setOnClickListener {
            finish()
        }

        recyclerView = cartBinding.rvCart
        recyclerView.layoutManager = LinearLayoutManager(this)

        displayCartItems()

    }

    private fun displayCartItems() {
        val cartItems = cartManager.getCartItems()

        cartAdapter = CartAdapter(cartItems, cartManager, this@CartActivity)
        recyclerView.adapter = cartAdapter

        //  Update total price
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        val (subTotal, total) = cartManager.calculateTotalPrice()
        cartBinding.apply {
            tvContentTotal.text = CommonUtils.formatRupiah(total)
            tvContentSubtotal.text = CommonUtils.formatRupiah(subTotal)

        }
    }

    override fun onPlusButtonClick(item: BatikItem) {
        cartManager.updateQuantity(item, item.quantity + 1)
        displayCartItems()
    }

    override fun onMinusButtonClick(item: BatikItem) {
        if (item.quantity > 1) {
            cartManager.updateQuantity(item, item.quantity - 1)
        } else {
            cartManager.removeItem(item)
        }
        displayCartItems()
    }

    override fun onRemoveButtonClick(item: BatikItem) {
        cartManager.removeItem(item)
        displayCartItems()
    }
}