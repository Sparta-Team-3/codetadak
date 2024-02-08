package com.goodness.codetadak.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.goodness.codetadak.api.responses.Item
import com.goodness.codetadak.api.responses.VideoItem
import com.goodness.codetadak.databinding.MyfavoritevideoListBinding

class MyFavoriteVideoAdapter() : RecyclerView.Adapter<MyFavoriteVideoAdapter.MyVideoHolder>() {
    private var myVideoList = mutableListOf<Item>()
    interface MyVideoItemClick {
        fun onClick(view: View, position: Int)
    }
    var myVideoItemClick: MyVideoItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFavoriteVideoAdapter.MyVideoHolder {
        return MyVideoHolder(MyfavoritevideoListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyFavoriteVideoAdapter.MyVideoHolder, position: Int) {
        holder.bind(myVideoList[position])
        holder.itemView.setOnClickListener {
            myVideoItemClick?.onClick(it,position)
        }
    }

    override fun getItemCount(): Int = myVideoList.size

    inner class MyVideoHolder(private val binding : MyfavoritevideoListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            with(binding) {
//                Glide.with(root).load(item.snippet.thumbnails.high.url).into(ivMyfavoriteThumbnail)
//                tvMyfavoriteTitle.setText(item.snippet.title)
//                tvMyfavoriteInfo.setText(item.snippet.description)

                // 더미 데이터 작업
                ivMyfavoriteThumbnail.setImageResource(item.thumbnail)
                tvMyfavoriteTitle.setText(item.title)
                tvMyfavoriteInfo.setText(item.description)

            }
        }
    }

    fun setData(newData : MutableList<Item>) {
        myVideoList = newData
        notifyDataSetChanged()
    }

    fun removeData(position: Int) {
        myVideoList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun dataAt(position: Int) = myVideoList[position]

    fun insertData(position: Int, item: Item) {
        myVideoList.add(position,item)
        notifyItemInserted(position)
    }
}