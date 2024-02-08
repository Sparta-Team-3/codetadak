package com.goodness.codetadak.adapters

import android.content.Context
import android.widget.ArrayAdapter

class SpinnerAdapter(context: Context, items: List<String>) : ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, items) {

}