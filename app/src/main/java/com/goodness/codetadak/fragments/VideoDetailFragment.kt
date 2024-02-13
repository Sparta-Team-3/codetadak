package com.goodness.codetadak.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.goodness.codetadak.R
import com.goodness.codetadak.databinding.FragmentVideoDetailBinding
import com.goodness.codetadak.sharedpreferences.App
import com.goodness.codetadak.viewmodels.LikeViewModel
import com.goodness.codetadak.viewmodels.YoutubeViewModel

class VideoDetailFragment : Fragment() {
	private var _binding: FragmentVideoDetailBinding? = null
	private val binding get() = _binding!!
	private val youtubeViewModel by lazy { ViewModelProvider(requireActivity())[YoutubeViewModel::class.java] }
	private val likeViewModel by lazy { ViewModelProvider(requireActivity())[LikeViewModel::class.java] }
	private var isFavorite = false

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		_binding = FragmentVideoDetailBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		youtubeViewModel.currentVideo.observe(viewLifecycleOwner) {
			binding.sflShimmerContainer.visibility = if (it.isError) View.VISIBLE else View.GONE

			if (it.dataList.isNotEmpty()) {
				with(binding) {
					Glide.with(root)
						.load(it.dataList[0].snippet.thumbnails.high.url)
						.into(ivThumbnail)
					Glide.with(root)
						.load(it.dataList[0].snippet.thumbnails.default.url)
						.into(ivProfile)
					tvVideoTitle.text = it.dataList[0].snippet.title
					tvChannelTitle.text = it.dataList[0].snippet.channelTitle
					tvDescription.text = it.dataList[0].snippet.description
					isFavorite =
						youtubeViewModel.currentVideo.value!!.dataList[0].snippet.isFavorite == true

					val isLike =
						likeViewModel.likeVideos.value?.any { videoItem -> videoItem.id == it.dataList[0].id }
							?: false

					binding.tvLike.text = getString(if (isLike) R.string.disLike else R.string.like)
					binding.icLike.setImageResource(
						if (isLike) {
							R.drawable.ic_like_filled
						} else {
							R.drawable.ic_like_empty
						}
					)
				}
			}
		}

		likeViewModel.likeVideos.observe(viewLifecycleOwner) {
			val currentVideo =
				if (youtubeViewModel.currentVideo.value?.dataList?.isEmpty() == true) null
				else youtubeViewModel.currentVideo.value?.dataList?.get(0)

			val isLike = it.any { videoItem -> videoItem.id == currentVideo?.id }

			binding.tvLike.text = getString(if (isLike) R.string.disLike else R.string.like)
			binding.icLike.setImageResource(
				if (isLike) {
					R.drawable.ic_like_filled
				} else {
					R.drawable.ic_like_empty
				}
			)
		}

		with(binding) {
			btnDown.setOnClickListener {
				requireActivity().supportFragmentManager.beginTransaction()
					.setCustomAnimations(R.anim.to_top, R.anim.from_bottom)
					.remove(this@VideoDetailFragment).commit()
				requireActivity().supportFragmentManager.popBackStack()
			}

			btnLike.setOnClickListener {
				val video = youtubeViewModel.currentVideo.value?.dataList?.get(0)
				likeViewModel.setLikeList(video!!)
			}

			btnShare.setOnClickListener {
				shareContent()
			}
			root.setOnTouchListener { _, event -> true }
		}

		requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
			override fun handleOnBackPressed() {
				// 뒤로 가기 시 실행되는 코드
				requireActivity().supportFragmentManager.beginTransaction()
					.setCustomAnimations(R.anim.to_top, R.anim.from_bottom)
					.remove(this@VideoDetailFragment).commit()
				requireActivity().supportFragmentManager.popBackStack()
			}
		})
	}


	override fun onResume() {
		super.onResume()
	}


	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun shareContent() {
		val sharingIntent = Intent(Intent.ACTION_SEND)
		with(sharingIntent) {
			type = "text/plain"
			putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
		}
		startActivity(Intent.createChooser(sharingIntent, "Share using"))
	}

}