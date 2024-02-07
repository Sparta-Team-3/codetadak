package com.goodness.codetadak.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.goodness.codetadak.R
import com.goodness.codetadak.adapters.HomeCategoryChannelsAdapter
import com.goodness.codetadak.adapters.HomeCategoryVideosAdapter
import com.goodness.codetadak.adapters.HomeMostViewedAdapter
import com.goodness.codetadak.adapters.SpinnerAdapter
import com.goodness.codetadak.api.responses.Item
import com.goodness.codetadak.databinding.FragmentHomeBinding
import com.goodness.codetadak.viewmodels.HomeViewModel

class HomeFragment : Fragment() {
	private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
	private val viewModel by lazy { HomeViewModel() }
	private val homeMostViewedAdapter by lazy { HomeMostViewedAdapter() }
	private val homeCategoryVideosAdapter by lazy { HomeCategoryVideosAdapter() }
	private val homeCategoryChannelsAdapter by lazy { HomeCategoryChannelsAdapter() }
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		// RecyclerView 가로 방향 설정 및 어댑터 연결
		val mostViewedData = listOf(
			Item(R.drawable.img_example, "Most 1", "Description Most 1"),
			Item(R.drawable.img_example, "Most 2", "Description Most 2"),
		)
		homeMostViewedAdapter.setData(mostViewedData)
		binding.rvMainMostViewedVideos.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
		binding.rvMainMostViewedVideos.adapter = homeMostViewedAdapter

		val categoryData = listOf(
			Item(R.drawable.img_example, "Category 1", "Description Category 1"),
			Item(R.drawable.img_example, "Category 2", "Description Category 2"),
		)
		homeCategoryVideosAdapter.setData(categoryData)
		binding.rvMainCategoryVideos.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
		binding.rvMainCategoryVideos.adapter = homeCategoryVideosAdapter

		val channelData = listOf(
			Item(R.drawable.img_example, "Channel 1", "Description Channel 1"),
			Item(R.drawable.img_example, "Channel 2", "Description Channel 2"),
		)
		homeCategoryChannelsAdapter.setData(channelData)
		binding.rvMainCategoryChannels.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
		binding.rvMainCategoryChannels.adapter = homeCategoryChannelsAdapter

		// Spinner 설정
		val items = listOf("Java", "Python", "Kotlin", "Swift", "Dart", "JavaScript", "C++", "C#", "Go", "Ruby", "Rust", "Scala", "TypeScript", "PHP", "HTML", "CSS")
		val adapter = SpinnerAdapter(requireContext(), items)
		binding.spinnerMainCategoryVideos.adapter = adapter

	}
}