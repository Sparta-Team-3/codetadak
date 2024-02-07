package com.goodness.codetadak.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast

class SpinnerAdapter(context: Context, items: List<String>) : ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false)
        val item = getItem(position)
        (view as TextView).apply {
            text = item
            setTextColor(Color.WHITE)
        }

        view.setOnClickListener {
            Toast.makeText(context, "$item 영상 목록입니다!!!", Toast.LENGTH_SHORT).show()
        }

        return view
    }

}