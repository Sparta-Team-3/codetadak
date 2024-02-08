package com.goodness.codetadak.fragments

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
import com.goodness.codetadak.viewmodels.YoutubeViewModel

class VideoDetailFragment : Fragment() {
    private var _binding: FragmentVideoDetailBinding? = null
    private val binding get() = _binding!!
    private val youtubeViewModel by lazy { ViewModelProvider(requireActivity())[YoutubeViewModel::class.java] }
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
                }
            }
        }

        with(binding) {
            btnDown.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.to_top, R.anim.from_bottom).remove(this@VideoDetailFragment).commit()
                requireActivity().supportFragmentManager.popBackStack()
            }

            icLike.setImageResource(
                if (isFavorite) {
                    R.drawable.ic_like_filled
                } else {
                    R.drawable.ic_like_empty
                }
            )

            btnLike.setOnClickListener {
                val video = youtubeViewModel.currentVideo.value?.dataList
                isFavorite = !isFavorite
                if (!isFavorite) {
                    icLike.setImageResource(R.drawable.ic_like_empty)
                    Toast.makeText(context, "좋아요 리스트에서 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    icLike.setImageResource(R.drawable.ic_like_filled)
                    Toast.makeText(context, "좋아요 리스트에 추가되었습니다.", Toast.LENGTH_SHORT).show()
                    if (video != null) {
                        App.prefs.saveMyFavorite(video)
                    }
                }
            }
            root.setOnTouchListener { _, event -> true }
        }

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