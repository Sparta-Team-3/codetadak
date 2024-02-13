package com.goodness.codetadak.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.goodness.codetadak.api.responses.ChannelItem
import com.goodness.codetadak.databinding.ItemHomeBinding

class HomeCategoryChannelsAdapter : RecyclerView.Adapter<HomeCategoryChannelsAdapter.ViewHolder>() {
    private var channels: List<ChannelItem> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoryChannelsAdapter.ViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeCategoryChannelsAdapter.ViewHolder, position: Int) {
        val channel = channels[position]
        Glide.with(holder.itemView.context)
            .load(channel.snippet.thumbnails.default.url)
            .into(holder.ivThumbnail)
        holder.tvTitle.text = channel.snippet.title
        holder.tvDescription.text = channel.snippet.description
    }

    override fun getItemCount(): Int {
        return channels.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    inner class ViewHolder(private val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivThumbnail = binding.ivItemHomeThumbnail
        val tvTitle = binding.tvItemHomeTitle
        val tvDescription = binding.tvItemHomeDescription
    }

    fun setData(channels: List<ChannelItem>) {
        this.channels = channels
        notifyDataSetChanged()
    }
}