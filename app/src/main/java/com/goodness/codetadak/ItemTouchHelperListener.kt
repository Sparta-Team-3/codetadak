package com.goodness.codetadak

import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface ItemTouchHelperListener {
    fun onItemSwipe(position: Int, view : RecyclerView.ViewHolder)
}