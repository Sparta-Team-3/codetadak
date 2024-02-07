package com.goodness.codetadak.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goodness.codetadak.api.responses.Item
import com.goodness.codetadak.databinding.ItemHomeBinding

class HomeCategoryChannelsAdapter : RecyclerView.Adapter<HomeCategoryChannelsAdapter.ViewHolder>() {
    private var items = listOf<Item>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoryChannelsAdapter.ViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeCategoryChannelsAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.ivThumbnail.setImageResource(item.thumbnail)
        holder.tvTitle.text = item.title
        holder.tvDescription.text = item.description
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

    fun setData(newData: List<Item>) {
        items = newData
        notifyDataSetChanged()
    }
}