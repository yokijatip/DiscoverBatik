@file:Suppress("DEPRECATION")

package com.enigma.discoverbatik.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.view.login.LoginActivity

object CommonUtils {

    fun alertError(context: Context, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        val view = LayoutInflater.from(context).inflate(R.layout.custom_error_alert, null)


        val tvMessage = view.findViewById<TextView>(R.id.tv_message)
        val btnGotit = view.findViewById<TextView>(R.id.btn_gotit)
        tvMessage.text = message

        builder.setView(view)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnGotit.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun alertSuccess(context: Context, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        val view = LayoutInflater.from(context).inflate(R.layout.custom_success_alert, null)


        val tvMessage = view.findViewById<TextView>(R.id.tv_message)
        val btnGotit = view.findViewById<TextView>(R.id.btn_gotit)
        tvMessage.text = message

        builder.setView(view)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnGotit.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
            (context as Activity).overridePendingTransition(
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )

        }
        dialog.show()
    }

}