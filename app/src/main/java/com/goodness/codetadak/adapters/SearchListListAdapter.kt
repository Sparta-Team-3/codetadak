package com.goodness.codetadak.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.goodness.codetadak.api.responses.SearchItem
import com.goodness.codetadak.databinding.SearchItemVideoBinding
import com.goodness.codetadak.viewmodels.YoutubeViewModel

class SearchListListAdapter(private val youtubeViewModel: YoutubeViewModel) :
	ListAdapter<SearchItem, SearchListListAdapter.SearchListHolder>(SearchItemDiffCallback()) {
	interface OnItemClickListener {
		fun onItemClick(position: Int)
	}

	private var listener: OnItemClickListener? = null

	fun setOnItemClickListener(listener: OnItemClickListener) {
		this.listener = listener
	}
	inner class SearchListHolder(private val binding: SearchItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(searchItem: SearchItem) = with(binding) {
			tvItemTitle.text = Html.fromHtml(searchItem.snippet.title, Html.FROM_HTML_MODE_LEGACY)

			Glide.with(root)
				.load(searchItem.snippet.thumbnails.high.url)
				.into(ivImage)

			root.setOnClickListener {
				youtubeViewModel.setCurrentVideoById(searchItem.id.videoId)
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, position: Int): SearchListHolder {
		val binding = SearchItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return SearchListHolder(binding)
	}

	override fun onBindViewHolder(holder: SearchListHolder, position: Int) {
		val searchItem = getItem(position)
		holder.bind(searchItem)
		holder.itemView.setOnClickListener {
			this.listener?.onItemClick(position)
		}
	}
}

class SearchItemDiffCallback : DiffUtil.ItemCallback<SearchItem>() {
	override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem) = oldItem === newItem

	override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
		return oldItem == newItem
	}
}