package com.goodness.codetadak.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.goodness.codetadak.R
import com.goodness.codetadak.databinding.FragmentVideoDetailBinding
import com.goodness.codetadak.sharedpreferences.App
import com.goodness.codetadak.viewmodels.YoutubeViewModel

class VideoDetailFragment : Fragment() {
    private var _binding: FragmentVideoDetailBinding? = null
    private val binding get() = _binding!!
    private val youtubeViewModel by lazy { ViewModelProvider(requireActivity())[YoutubeViewModel::class.java] }

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
            if (it.dataList.isNotEmpty()) {
                with(binding) {
                    Glide.with(root)
                        .load(it.dataList[0].snippet.thumbnails.high.url)
                        .into(ivThumbnail)
                    tvVideoTitle.text = it.dataList[0].snippet.title
                    tvChannelTitle.text = it.dataList[0].snippet.channelTitle
                    tvDescription.text = it.dataList[0].snippet.description
                }
            }
        }

        binding.btnDown.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.to_top, R.anim.from_bottom).remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.btnLike.setOnClickListener {
            val video = youtubeViewModel.currentVideo.value?.dataList
            if (video != null) {
                App.prefs.saveMyFavorite(video) // 저장된것을 불러와서 중첩으로 쌓아야
                App.prefs.loadMyFavorite() // 있는경우 넣어주고 없는경우 필터로 빼줘야
            }
        }
        binding.root.setOnTouchListener { _, event -> true }

        requireActivity().onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 뒤로 가기 시 실행되는 코드
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.to_top, R.anim.from_bottom).remove(this@VideoDetailFragment).commit()
                requireActivity().supportFragmentManager.popBackStack()
            }
        })
    }


    override fun onResume() {
        super.onResume()
    }
}