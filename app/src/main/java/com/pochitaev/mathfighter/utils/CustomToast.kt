package com.pochitaev.mathfighter.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.pochitaev.mathfighter.R

fun showCustomToast(context: Context, message: String) {
    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val toastView = inflater.inflate(R.layout.toast, null)

    val toastText = toastView.findViewById<TextView>(R.id.toastText)
    toastText.text = message

    val toast = Toast(context)
    toast.duration = Toast.LENGTH_SHORT
    toast.view = toastView
    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
    toast.setGravity(Gravity.BOTTOM, 0, 0)
    toast.show()
}