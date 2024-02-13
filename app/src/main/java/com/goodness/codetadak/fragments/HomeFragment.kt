package com.goodness.codetadak.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.goodness.codetadak.CircleProgressDialog
import com.goodness.codetadak.MainActivity
import com.goodness.codetadak.R
import com.goodness.codetadak.adapters.HomeCategoryChannelsAdapter
import com.goodness.codetadak.adapters.HomeCategoryVideosAdapter
import com.goodness.codetadak.adapters.HomeMostViewedAdapter
import com.goodness.codetadak.adapters.SearchListListAdapter
import com.goodness.codetadak.adapters.SpinnerAdapter
import com.goodness.codetadak.databinding.FragmentHomeBinding
import com.goodness.codetadak.viewmodels.HomeViewModel
import com.goodness.codetadak.viewmodels.LikeViewModel
import com.goodness.codetadak.viewmodels.YoutubeViewModel

class HomeFragment : Fragment() {
	private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
	private val viewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }
	private val homeMostViewedAdapter by lazy { HomeMostViewedAdapter(youtubeViewModel) }
	private val homeCategoryVideosAdapter by lazy { HomeCategoryVideosAdapter(youtubeViewModel) }
	private val homeCategoryChannelsAdapter by lazy { HomeCategoryChannelsAdapter() }
	private val youtubeViewModel by lazy { ViewModelProvider(requireActivity())[YoutubeViewModel::class.java] }
	private val likeViewModel by lazy { ViewModelProvider(requireActivity())[LikeViewModel::class.java] }
	private var loadingDialog = CircleProgressDialog()

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

		homeMostViewedAdapter.setOnItemClickListener(object : SearchListListAdapter.OnItemClickListener {
			override fun onItemClick(position: Int) {
				(requireActivity() as MainActivity).replace()
			}
		})

		binding.rvMainCategoryVideos.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
		binding.rvMainCategoryVideos.adapter = homeCategoryVideosAdapter

		binding.rvMainCategoryChannels.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
		binding.rvMainCategoryChannels.adapter = homeCategoryChannelsAdapter

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
			// 카테고리 별 비디오 목록 조회
			response.items.forEach { category ->
				viewModel.getVideosByCategory(category.id, "KR")
				viewModel.videosByCategoryResponse.observe(viewLifecycleOwner, Observer { videosResponse ->
					// RecyclerView에 비디오 목록 설정
					homeCategoryVideosAdapter.setData(videosResponse.items)
				})
			}
		})


		binding.spinnerMainCategoryVideos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
				// 선택된 카테고리에 속하는 비디오 목록 조회
				val selectedCategory = viewModel.videoCategoriesResponse.value?.items?.get(position)
				selectedCategory?.let { category ->
					viewModel.getVideosByCategory(category.id, "KR")
//					viewModel.getChannelsByCategory(category.id, "KR")
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
				val videoDetailFragment = VideoDetailFragment()

				// FragmentManager를 사용하여 VideoDetailFragment로 이동
				val fragmentManager = requireActivity().supportFragmentManager
				val fragmentTransaction = fragmentManager.beginTransaction()
				fragmentTransaction.setCustomAnimations(R.anim.to_top, R.anim.from_bottom)
				fragmentTransaction.replace(R.id.video_detail_container, videoDetailFragment)
				fragmentTransaction.commit()
			}
		})

		homeCategoryVideosAdapter.setOnItemClickListener(object : SearchListListAdapter.OnItemClickListener {
			override fun onItemClick(position: Int) {
				val selectedVideo = homeCategoryVideosAdapter.getItem(position)
				viewModel.selectedVideo.value = selectedVideo
				val videoDetailFragment = VideoDetailFragment()

				// FragmentManager를 사용하여 VideoDetailFragment로 이동
				val fragmentManager = requireActivity().supportFragmentManager
				val fragmentTransaction = fragmentManager.beginTransaction()
				fragmentTransaction.setCustomAnimations(R.anim.to_top, R.anim.from_bottom)
				fragmentTransaction.replace(R.id.video_detail_container, videoDetailFragment)
				fragmentTransaction.commit()
			}
		})
	}
}