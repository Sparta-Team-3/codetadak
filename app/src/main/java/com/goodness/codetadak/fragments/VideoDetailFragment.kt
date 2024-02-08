package com.goodness.codetadak.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.goodness.codetadak.R
import com.goodness.codetadak.api.responses.VideoItem
import com.goodness.codetadak.databinding.FragmentVideoDetailBinding
import com.goodness.codetadak.sharedpreferences.App
import com.goodness.codetadak.viewmodels.DataState
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
                App.prefs.saveMyFavorite(video)
            }
        }
        binding.root.setOnTouchListener { _, event -> true }
    }

    override fun onResume() {
        super.onResume()
    }
}