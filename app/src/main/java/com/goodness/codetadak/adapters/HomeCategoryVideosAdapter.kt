package com.goodness.codetadak.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.goodness.codetadak.api.responses.VideoItem
import com.goodness.codetadak.databinding.ItemHomeBinding

class HomeCategoryVideosAdapter : RecyclerView.Adapter<HomeCategoryVideosAdapter.ViewHolder>() {
    private var items: List<VideoItem> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoryVideosAdapter.ViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeCategoryVideosAdapter.ViewHolder, position: Int) {
        val item = items[position]
        Glide.with(holder.ivThumbnail.context)
            .load(item.snippet.thumbnails.default.url)
            .into(holder.ivThumbnail)
        holder.tvTitle.text = item.snippet.title
        holder.tvDescription.text = item.snippet.description
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    inner class ViewHolder(private val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivThumbnail = binding.ivItemHomeThumbnail
        val tvTitle = binding.tvItemHomeTitle
        val tvDescription = binding.tvItemHomeDescription

    }

    fun setData(items: List<VideoItem>) {
        this.items = items
        notifyDataSetChanged()
    }
}