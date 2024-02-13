package com.goodness.codetadak.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goodness.codetadak.api.responses.VideoItem

class LikeViewModel : ViewModel() {
	private var _likeVideos: MutableLiveData<List<VideoItem>> = MutableLiveData(emptyList())

	val likeVideos: LiveData<List<VideoItem>>
		get() = _likeVideos

	fun setLikeList(newVideo: VideoItem) {
		val isHasVideo = _likeVideos.value?.any { video -> video.id == newVideo.id } ?: false

		_likeVideos.value = if (isHasVideo) {
			_likeVideos.value?.filterNot { video -> video.id == newVideo.id }
		} else {
			_likeVideos.value?.plus(newVideo)
		}
	}
}