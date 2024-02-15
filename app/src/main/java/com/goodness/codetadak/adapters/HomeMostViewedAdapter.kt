package com.goodness.codetadak.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.goodness.codetadak.api.responses.VideoItem
import com.goodness.codetadak.databinding.ItemHomeBinding
import com.goodness.codetadak.viewmodels.YoutubeViewModel

class HomeMostViewedAdapter(
	private val youtubeViewModel: YoutubeViewModel
) : RecyclerView.Adapter<HomeMostViewedAdapter.ViewHolder>() {

	private var listener: SearchListListAdapter.OnItemClickListener? = null

	private var items: List<VideoItem> = listOf()
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMostViewedAdapter.ViewHolder {
		val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(binding)
	}

	fun setOnItemClickListener(listener: SearchListListAdapter.OnItemClickListener) {
		this.listener = listener
	}

	override fun onBindViewHolder(holder: HomeMostViewedAdapter.ViewHolder, position: Int) {
		val item = items[position]
		Glide.with(holder.ivThumbnail.context)
			.load(item.snippet.thumbnails.default.url)
			.into(holder.ivThumbnail)
		holder.tvTitle.text = item.snippet.title
		holder.tvDescription.text = item.snippet.description
		holder.root.setOnClickListener {
			this.listener?.onItemClick(position)
			youtubeViewModel.setCurrentVideoById(item.id)
		}
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
		val root = binding.root
	}

	fun setData(items: List<VideoItem>) {
		this.items = items
		notifyDataSetChanged()
	}

	fun getItem(position: Int): VideoItem {
		return items[position]
	}

}