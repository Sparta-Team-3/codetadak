package com.goodness.codetadak.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.goodness.codetadak.ItemTouchHelperListener
import com.goodness.codetadak.R
import com.goodness.codetadak.viewmodels.LikeViewModel
import com.goodness.codetadak.viewmodels.YoutubeViewModel
import com.google.android.material.snackbar.Snackbar

// 롱터치 후 드래그, 스와이프 동작 제어
class SwipeHelperCallback(private val context : Context, listener : ItemTouchHelperListener) :
    ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
    private var itemTouchHelperListener: ItemTouchHelperListener = listener
    // 드래그 일어날 때 동작 (롱터치 후 드래그)
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    // 스와이프 일어날 때 동작
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//        val position = viewHolder.layoutPosition
//        val data = rviewAdapter.dataAt(position)
        itemTouchHelperListener.onItemSwipe(viewHolder.layoutPosition, viewHolder)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        // actionState가 SWIPE 동작일 때 배경을 분홍색으로 칠하는 작업을 수행하도록 함
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val itemView = viewHolder.itemView
            val height = (itemView.bottom - itemView.top).toFloat()
            val width = height / 4
            val paint = Paint()
            if (dX < 0) {  // 왼쪽으로 스와이프하는지 확인
                // ViewHolder의 백그라운드에 깔아줄 사각형의 크기와 색상을 지정
                paint.color = Color.parseColor("#E384FF")
                val background = RectF(
                    itemView.right.toFloat() + dX,
                    itemView.top.toFloat(),
                    itemView.right.toFloat(),
                    itemView.bottom.toFloat())
                c.drawRect(background, paint)


                // 휴지통 아이콘과 표시될 위치를 지정하고 비트맵을 그려줌
                val icon: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_myvideo_trash)
                val iconDst = RectF(
                    itemView.right.toFloat() - 3 * width,
                    itemView.top.toFloat() + width,
                    itemView.right.toFloat() - width,
                    itemView.bottom.toFloat() - width)
                c.drawBitmap(icon, null, iconDst, null)
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        return
    }
}

