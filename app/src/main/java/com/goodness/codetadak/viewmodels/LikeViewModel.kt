package com.goodness.codetadak.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goodness.codetadak.api.responses.VideoItem
import com.goodness.codetadak.sharedpreferences.App

class LikeViewModel : ViewModel() {
	private var _likeVideos: MutableLiveData<List<VideoItem>> = MutableLiveData(App.prefs.loadMyFavorite())

	val likeVideos: LiveData<List<VideoItem>>
		get() = _likeVideos

	fun setLikeList(newVideo: VideoItem) {
		val isHasVideo = _likeVideos.value?.any { video -> video.id == newVideo.id } ?: false

		val result = if (isHasVideo) {
			_likeVideos.value?.filterNot { video -> video.id == newVideo.id }?.sortedBy { it.snippet.title }
		} else {
			_likeVideos.value?.plus(newVideo)?.sortedBy { it.snippet.title }
		} ?: emptyList()

		_likeVideos.value = result
		App.prefs.saveMyFavorite(result)
	}
}