package com.goodness.codetadak.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.goodness.codetadak.api.responses.Item
import com.goodness.codetadak.api.responses.VideoItem
import com.goodness.codetadak.databinding.MyfavoritevideoListBinding
import com.goodness.codetadak.viewmodels.YoutubeViewModel

class MyFavoriteVideoAdapter(private val youtubeViewModel: YoutubeViewModel) : RecyclerView.Adapter<MyFavoriteVideoAdapter.MyVideoHolder>() {
    private var myVideoList = mutableListOf<VideoItem>()
    interface MyVideoItemClick {
        fun onClick(position: Int)
    }
    var myVideoItemClick: MyVideoItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFavoriteVideoAdapter.MyVideoHolder {
        return MyVideoHolder(MyfavoritevideoListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyFavoriteVideoAdapter.MyVideoHolder, position: Int) {
        holder.bind(myVideoList[position])
        holder.itemView.setOnClickListener {
            myVideoItemClick?.onClick(position)
            youtubeViewModel
        }
    }

    override fun getItemCount(): Int = myVideoList.size

    inner class MyVideoHolder(private val binding : MyfavoritevideoListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoItem) {
            with(binding) {
                Glide.with(root).load(item.snippet.thumbnails.high.url).into(ivMyfavoriteThumbnail)
                tvMyfavoriteTitle.text = Html.fromHtml(item.snippet.title, Html.FROM_HTML_MODE_LEGACY)
                tvMyfavoriteInfo.setText(item.snippet.description)

            }
        }
    }

    fun setData(newData : MutableList<VideoItem>) {
        myVideoList = newData
        notifyDataSetChanged()
    }

    fun removeData(position: Int) {
        myVideoList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun dataAt(position: Int) = myVideoList[position]

    fun insertData(position: Int, item: VideoItem) {
        myVideoList.add(position,item)
        notifyItemInserted(position)
    }
}