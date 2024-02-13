package com.goodness.codetadak.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.goodness.codetadak.CircleProgressDialog
import com.goodness.codetadak.MainActivity
import com.goodness.codetadak.adapters.HomeCategoryChannelsAdapter
import com.goodness.codetadak.adapters.HomeCategoryVideosAdapter
import com.goodness.codetadak.adapters.HomeMostViewedAdapter
import com.goodness.codetadak.adapters.SearchListListAdapter
import com.goodness.codetadak.adapters.SpinnerAdapter
import com.goodness.codetadak.databinding.FragmentHomeBinding
import com.goodness.codetadak.viewmodels.HomeViewModel
import com.goodness.codetadak.viewmodels.LikeViewModel
import com.goodness.codetadak.viewmodels.YoutubeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
	private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
	private lateinit var viewModel: HomeViewModel
	private val youtubeViewModel by lazy { ViewModelProvider(requireActivity())[YoutubeViewModel::class.java] }
	private val likeViewModel by lazy { ViewModelProvider(requireActivity())[LikeViewModel::class.java] }
	private var loadingDialog = CircleProgressDialog()

	private lateinit var homeMostViewedAdapter: HomeMostViewedAdapter
	private lateinit var homeCategoryVideosAdapter: HomeCategoryVideosAdapter
	private lateinit var homeCategoryChannelsAdapter: HomeCategoryChannelsAdapter


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
		homeMostViewedAdapter = HomeMostViewedAdapter(youtubeViewModel)
		homeCategoryVideosAdapter = HomeCategoryVideosAdapter()
		homeCategoryChannelsAdapter = HomeCategoryChannelsAdapter()

		binding.rvMainMostViewedVideos.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
		binding.rvMainMostViewedVideos.adapter = homeMostViewedAdapter

		homeMostViewedAdapter.setOnItemClickListener(object : SearchListListAdapter.OnItemClickListener {
			override fun onItemClick(position: Int) {
				Log.d("asd", "asd: $position")
				showLoading()
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

		// 비디오 카테고리 조회
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

	}

	private fun showLoading() {
		CoroutineScope(Dispatchers.Main).launch {
			loadingDialog.show(parentFragmentManager, loadingDialog.tag)
			withContext(Dispatchers.Default) { delay(1500) }
			loadingDialog.dismiss()
		}
	}
}