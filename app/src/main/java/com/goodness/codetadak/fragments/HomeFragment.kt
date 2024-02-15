package com.goodness.codetadak.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goodness.codetadak.MainActivity
import com.goodness.codetadak.adapters.HomeCategoryChannelsAdapter
import com.goodness.codetadak.adapters.HomeCategoryVideosAdapter
import com.goodness.codetadak.adapters.HomeMostViewedAdapter
import com.goodness.codetadak.adapters.SearchListListAdapter
import com.goodness.codetadak.adapters.SpinnerAdapter
import com.goodness.codetadak.databinding.FragmentHomeBinding
import com.goodness.codetadak.viewmodels.HomeViewModel
import com.goodness.codetadak.viewmodels.YoutubeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
	private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
	private val viewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }
	private val youtubeViewModel by lazy { ViewModelProvider(requireActivity())[YoutubeViewModel::class.java] }
	private val homeMostViewedAdapter by lazy { HomeMostViewedAdapter(youtubeViewModel) }
	private val homeCategoryVideosAdapter by lazy { HomeCategoryVideosAdapter(youtubeViewModel) }
	private val homeCategoryChannelsAdapter by lazy { HomeCategoryChannelsAdapter() }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		binding.rvMainMostViewedVideos.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
		binding.rvMainMostViewedVideos.adapter = homeMostViewedAdapter
		binding.rvMainMostViewedVideos.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
			override fun onInterceptTouchEvent(view: RecyclerView, event: MotionEvent): Boolean {
				when (event.action) {
					MotionEvent.ACTION_DOWN -> {
						binding.rvMainMostViewedVideos.parent?.requestDisallowInterceptTouchEvent(true)
					}
				}
				return false
			}

			override fun onTouchEvent(view: RecyclerView, event: MotionEvent) {}
			override fun onRequestDisallowInterceptTouchEvent(view: Boolean) {}
		})

		homeMostViewedAdapter.setOnItemClickListener(object : SearchListListAdapter.OnItemClickListener {
			override fun onItemClick(position: Int) {
				(requireActivity() as MainActivity).replace()
			}
		})

		binding.rvMainCategoryVideos.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
		binding.rvMainCategoryVideos.adapter = homeCategoryVideosAdapter
		binding.rvMainCategoryVideos.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
			override fun onInterceptTouchEvent(view: RecyclerView, event: MotionEvent): Boolean {
				when (event.action) {
					MotionEvent.ACTION_DOWN -> {
						binding.rvMainCategoryVideos.parent?.requestDisallowInterceptTouchEvent(true)
					}
				}
				return false
			}

			override fun onTouchEvent(view: RecyclerView, event: MotionEvent) {}
			override fun onRequestDisallowInterceptTouchEvent(view: Boolean) {}
		})

		binding.rvMainCategoryChannels.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
		binding.rvMainCategoryChannels.adapter = homeCategoryChannelsAdapter
		binding.rvMainCategoryChannels.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
			override fun onInterceptTouchEvent(view: RecyclerView, event: MotionEvent): Boolean {
				when (event.action) {
					MotionEvent.ACTION_DOWN -> {
						binding.rvMainCategoryChannels.parent?.requestDisallowInterceptTouchEvent(true)
					}
				}
				return false
			}

			override fun onTouchEvent(view: RecyclerView, event: MotionEvent) {}
			override fun onRequestDisallowInterceptTouchEvent(view: Boolean) {}
		})

		// 가장 인기 있는 비디오 목록 조회
		viewModel.videosResponse.observe(viewLifecycleOwner, Observer { response ->
			// videosResponse 변수에는 가장 인기 있는 비디오 목록이 있습니다.
			homeMostViewedAdapter.setData(response.items)
		})

		viewModel.getMostPopularVideos()

		// 비디오 카테고리 조회 및 Spinner에 설정 (한국 지역, 한국어)
		viewModel.getVideoCategories("KR", "ko")
		viewModel.videoCategoriesResponse.observe(viewLifecycleOwner, Observer { response ->
			val categories = response.items.map { it.snippet.title }
			val adapter = SpinnerAdapter(requireContext(), categories)
			binding.spinnerMainCategoryVideos.adapter = adapter
			// 카테고리 별 비디오 목록 조회 (초기화면은 첫번째 카테고리에 속하는 비디오 목록 조회)
			val firstCategory = response.items.firstOrNull()
			firstCategory?.let { category ->
				val scope = CoroutineScope(Dispatchers.IO)
				scope.launch {
					viewModel.getVideosByCategory(category.id, "KR")
					viewModel.getChannelsByCategory(category.id, "KR")
					withContext(Dispatchers.Main) {
						viewModel.videosByCategoryResponse.observe(viewLifecycleOwner, Observer { videosResponse ->
							// RecyclerView에 비디오 목록 설정
							homeCategoryVideosAdapter.setData(videosResponse.items)
						})

						viewModel.channelResponse.observe(viewLifecycleOwner, Observer { channelsResponse ->
							// RecyclerView에 채널 정보 설정
							homeCategoryChannelsAdapter.setData(channelsResponse.items)
						})
					}
				}
			}
		})


		binding.spinnerMainCategoryVideos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
				// 선택된 카테고리에 속하는 비디오 목록 조회
				val selectedCategory = viewModel.videoCategoriesResponse.value?.items?.get(position)
				selectedCategory?.let { category ->
					viewModel.getVideosByCategory(category.id, "KR")
					viewModel.getChannelsByCategory(category.id, "KR")
				}

			}

			override fun onNothingSelected(parent: AdapterView<*>?) {
				// 아무것도 선택되지 않았을 때의 처리
				viewModel.getMostPopularVideos()
			}
		}

		viewModel.videosBySelectedCategoryResponse.observe(viewLifecycleOwner, Observer { response ->
			// RecyclerView에 비디오 목록 설정
			homeCategoryVideosAdapter.setData(response.items)
		})

		viewModel.channelResponse.observe(viewLifecycleOwner, Observer { response ->
			// RecyclerView에 채널 정보 설정
			homeCategoryChannelsAdapter.setData(response.items)
		})

		// 영상의 thumbnail을 클릭햇을 때 VideoDetailFragment로 이동
		homeMostViewedAdapter.setOnItemClickListener(object : SearchListListAdapter.OnItemClickListener {
			override fun onItemClick(position: Int) {
				val selectedVideo = homeMostViewedAdapter.getItem(position)
				viewModel.selectedVideo.value = selectedVideo

				// FragmentManager를 사용하여 VideoDetailFragment로 이동
				(activity as? MainActivity)?.replace()
			}
		})

		homeCategoryVideosAdapter.setOnItemClickListener(object : SearchListListAdapter.OnItemClickListener {
			override fun onItemClick(position: Int) {
				val selectedVideo = homeCategoryVideosAdapter.getItem(position)
				viewModel.selectedVideo.value = selectedVideo

				// FragmentManager를 사용하여 VideoDetailFragment로 이동
				(activity as? MainActivity)?.replace()
			}
		})
	}
}