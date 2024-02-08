package com.goodness.codetadak.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.goodness.codetadak.R
import com.goodness.codetadak.api.responses.LanguageItem
import com.goodness.codetadak.databinding.SearchItemLanguageBinding
import com.goodness.codetadak.viewmodels.LanguageViewModel

class LanguageListAdapter(
	private val context: Context,
	private val onRootClick: (data: LanguageItem) -> Unit
) :
	ListAdapter<LanguageItem, LanguageListAdapter.LanguageListHolder>(LanguageItemDiffCallback()) {
	inner class LanguageListHolder(private val binding: SearchItemLanguageBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(data: LanguageItem) = with(binding) {
			tvName.text = data.name
			tvName.setTextColor(
				context.getColor(
					if (data.isSelected) R.color.background else R.color.dark_pink
				)
			)

			root.setBackgroundResource(
				if (data.isSelected) R.drawable.round_with_background_selected else R.drawable.round_with_background
			)

			root.setOnClickListener {
				onRootClick(data)
			}
		}
	}


	override fun onCreateViewHolder(parent: ViewGroup, position: Int): LanguageListAdapter.LanguageListHolder {
		val binding = SearchItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return LanguageListHolder(binding)
	}

	override fun onBindViewHolder(holder: LanguageListAdapter.LanguageListHolder, position: Int) {
		val data = getItem(position)
		holder.bind(data)
	}
}

class LanguageItemDiffCallback : DiffUtil.ItemCallback<LanguageItem>() {
	override fun areItemsTheSame(oldItem: LanguageItem, newItem: LanguageItem) = oldItem.name == newItem.name

	override fun areContentsTheSame(oldItem: LanguageItem, newItem: LanguageItem): Boolean {
		return oldItem == newItem
	}
}