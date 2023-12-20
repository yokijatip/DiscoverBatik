package com.enigma.discoverbatik.models.adapter.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.data.remote.response.BatikItem
import com.enigma.discoverbatik.utils.CommonUtils
import com.enigma.discoverbatik.view.activity.cart.CartManager

class CartAdapter(
    private val cartItems: List<BatikItem>,
    private val cartManager: CartManager,
    private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onPlusButtonClick(item: BatikItem)
        fun onMinusButtonClick(item: BatikItem)
        fun onRemoveButtonClick(item: BatikItem)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvContentTitle: TextView = itemView.findViewById(R.id.tv_content_title)
        val tvContentPrice: TextView = itemView.findViewById(R.id.tv_content_price)
        val tvContentQuantity: TextView = itemView.findViewById(R.id.tv_quantity)

        val btnAdd: ImageView = itemView.findViewById(R.id.btn_plus)
        val btnMinus: ImageView = itemView.findViewById(R.id.btn_minus)
        val btnRemove: LinearLayout = itemView.findViewById(R.id.btn_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        val cartItem = cartItems[position]

        holder.tvContentTitle.text = cartItem.name
        holder.tvContentPrice.text = CommonUtils.formatRupiah(cartItem.price)
        holder.tvContentQuantity.text = cartItem.quantity.toString()

        holder.btnAdd.setOnClickListener { onItemClickListener.onPlusButtonClick(cartItem) }
        holder.btnMinus.setOnClickListener { onItemClickListener.onMinusButtonClick(cartItem) }
        holder.btnRemove.setOnClickListener { onItemClickListener.onRemoveButtonClick(cartItem) }
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }


}