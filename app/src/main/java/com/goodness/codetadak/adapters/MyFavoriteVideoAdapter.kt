package com.goodness.codetadak.adapters

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.goodness.codetadak.ItemTouchHelperListener
import com.goodness.codetadak.R
import com.goodness.codetadak.api.responses.VideoItem
import com.goodness.codetadak.databinding.MyfavoritevideoListBinding
import com.goodness.codetadak.viewmodels.LikeViewModel
import com.goodness.codetadak.viewmodels.YoutubeViewModel
import com.google.android.material.snackbar.Snackbar

class MyFavoriteVideoAdapter(private val youtubeViewModel: YoutubeViewModel, private val likeViewModel: LikeViewModel) : RecyclerView.Adapter<MyFavoriteVideoAdapter.MyVideoHolder>(), ItemTouchHelperListener {
    private var myVideoList = listOf<VideoItem>()
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
            youtubeViewModel.setCurrentVideoById(myVideoList[position].id)
        }
    }

    override fun getItemCount(): Int = myVideoList.size

    inner class MyVideoHolder(private val binding : MyfavoritevideoListBinding) : ViewHolder(binding.root) {
        fun bind(item: VideoItem) {
            with(binding) {
                Glide.with(root).load(item.snippet.thumbnails.high.url).into(ivMyfavoriteThumbnail)
                tvMyfavoriteTitle.text = Html.fromHtml(item.snippet.title, Html.FROM_HTML_MODE_LEGACY)
                tvMyfavoriteName.setText(item.snippet.channelTitle)

            }
        }
    }

    fun setData(newData : List<VideoItem>) {
        myVideoList = newData
        notifyDataSetChanged()
    }

    override fun onItemSwipe(position: Int, view: ViewHolder) {
        // 정렬
        val likeVideo = likeViewModel.likeVideos.value?.get(position)
        likeViewModel.setLikeList(likeVideo!!)
        Snackbar.make(view.itemView, R.string.snackbar_delete_info, 2500).setAction(R.string.snackbar_delete_cancel){
            likeViewModel.setLikeList(likeVideo)
        }.show()

    }
}