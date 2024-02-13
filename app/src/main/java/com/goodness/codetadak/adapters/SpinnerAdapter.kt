package com.goodness.codetadak.adapters

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class SpinnerAdapter(context: Context, private val categories: List<String>) : ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, categories) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view as TextView

        // 선택된 카테고리명을 텍스트 뷰에 설정
        textView.text = categories[position]

        // 드롭다운 메뉴의 텍스트 색상을 흰색으로 설정
        textView.setTextColor(Color.WHITE)

        // 배경색을 검은색으로 설정
        textView.setBackgroundColor(Color.BLACK)

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val textView = view as TextView

        // 드롭다운 메뉴의 텍스트 색상을 흰색으로 설정
        textView.setTextColor(Color.WHITE)

        // 배경색을 검은색으로 설정
        textView.setBackgroundColor(Color.BLACK)

        return view
    }
}