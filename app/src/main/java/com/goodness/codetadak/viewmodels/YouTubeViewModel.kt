package com.goodness.codetadak.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goodness.codetadak.api.YouTubeInstance
import com.goodness.codetadak.api.responses.CategoryItem
import com.goodness.codetadak.api.responses.ChannelItem
import com.goodness.codetadak.api.responses.SearchItem
import com.goodness.codetadak.api.responses.VideoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class DataState<T>(
	val dataList: List<T> = emptyList(),
	val isLoading: Boolean = false,
	val isError: Boolean = false,
	val errorCode: Int? = null
)

class YoutubeViewModel : ViewModel() {
	private val service = YouTubeInstance.getService()

	private var _mostPopularVideoState: MutableLiveData<DataState<VideoItem>> = MutableLiveData(DataState())
	private var _categoryState: MutableLiveData<DataState<CategoryItem>> = MutableLiveData(DataState())
	private var _mostPopularVideosByCategoryIdState: MutableLiveData<DataState<VideoItem>> = MutableLiveData(DataState())
	private var _searchVideoState: MutableLiveData<DataState<SearchItem>> = MutableLiveData(DataState())
	private var _channelsByCategoryId: MutableLiveData<DataState<ChannelItem>> = MutableLiveData(DataState())

	private var _currentVideo: MutableLiveData<DataState<VideoItem>> = MutableLiveData(DataState())

	val mostPopularVideoState: LiveData<DataState<VideoItem>>
		get() = _mostPopularVideoState
	val categoryState: LiveData<DataState<CategoryItem>>
		get() = _categoryState
	val mostPopularVideosByCategorySIdtate: LiveData<DataState<VideoItem>>
		get() = _mostPopularVideosByCategoryIdState
	val searchVideoState: LiveData<DataState<SearchItem>>
		get() = _searchVideoState
	val channelsByCategoryId: LiveData<DataState<ChannelItem>>
		get() = _channelsByCategoryId

	val currentVideo: LiveData<DataState<VideoItem>>
		get() = _currentVideo

	fun getMostPopularVideos(
		maxResults: Int = 5,
		regionCode: String = "KR"
	) {
		_mostPopularVideoState.value = _mostPopularVideoState.value?.copy(
			dataList = emptyList(),
			isLoading = true,
			isError = false,
			errorCode = null
		)

		CoroutineScope(Dispatchers.IO).launch {
			val response = service.getVideos(
				maxResults = maxResults,
				regionCode = regionCode
			)

			withContext(Dispatchers.Main) {
				if (response.isSuccessful) {
					_mostPopularVideoState.value = _mostPopularVideoState.value?.copy(
						dataList = response.body()?.items ?: emptyList(),
						isLoading = false,
						isError = false,
						errorCode = null
					)
				} else {
					_mostPopularVideoState.value = _mostPopularVideoState.value?.copy(
						dataList = emptyList(),
						isLoading = false,
						isError = true,
						errorCode = response.code()
					)
				}
			}
		}
	}

	fun getMostPopularVideosByCategoryId(
		categoryId: Int,
		maxResults: Int = 5,
		regionCode: String = "KR"
	) {
		_mostPopularVideosByCategoryIdState.value = _mostPopularVideosByCategoryIdState.value?.copy(
			dataList = emptyList(),
			isLoading = true,
			isError = false,
			errorCode = null
		)

		CoroutineScope(Dispatchers.IO).launch {
			val response = service.getVideos(
				videoCategoryId = categoryId,
				maxResults = maxResults,
				regionCode = regionCode
			)

			withContext(Dispatchers.Main) {
				if (response.isSuccessful) {
					_mostPopularVideosByCategoryIdState.value = _mostPopularVideosByCategoryIdState.value?.copy(
						dataList = response.body()?.items ?: emptyList(),
						isLoading = false,
						isError = false,
						errorCode = null
					)
				} else {
					_mostPopularVideosByCategoryIdState.value = _mostPopularVideosByCategoryIdState.value?.copy(
						dataList = emptyList(),
						isLoading = false,
						isError = true,
						errorCode = response.code()
					)
				}
			}
		}
	}

	fun setCurrentVideoById(id: String) {
		_currentVideo.value = _currentVideo.value?.copy(
			dataList = emptyList(),
			isLoading = true,
			isError = false,
			errorCode = null,
		)

		CoroutineScope(Dispatchers.IO).launch {
			val response = service.getVideo(
				id = id,
			)

			withContext(Dispatchers.Main) {
				if (response.isSuccessful) {
					_currentVideo.value = _currentVideo.value?.copy(
						dataList = response.body()?.items ?: emptyList(),
						isLoading = false,
						isError = false,
						errorCode = null
					)
				} else {
					_currentVideo.value = _currentVideo.value?.copy(
						dataList = emptyList(),
						isLoading = false,
						isError = true,
						errorCode = response.code()
					)
				}
			}
		}
	}

	fun getCategories(regionCode: String = "KR", hl: String = "ko_KR") {
		_categoryState.value = _categoryState.value?.copy(
			dataList = emptyList(),
			isLoading = true,
			isError = false,
			errorCode = null
		)

		CoroutineScope(Dispatchers.IO).launch {
			val response = service.getVideoCategories(
				regionCode = regionCode,
				hl = hl
			)

			withContext(Dispatchers.Main) {
				if (response.isSuccessful) {
					_categoryState.value = _categoryState.value?.copy(
						dataList = response.body()?.items ?: emptyList(),
						isLoading = false,
						isError = false,
						errorCode = null
					)
				} else {
					_categoryState.value = _categoryState.value?.copy(
						dataList = emptyList(),
						isLoading = false,
						isError = true,
						errorCode = response.code()
					)
				}
			}
		}
	}

	fun getSearchByKeyword(q: String, maxResults: Int = 5) {
		_searchVideoState.value = _searchVideoState.value?.copy(
			dataList = emptyList(),
			isLoading = true,
			isError = false,
			errorCode = null
		)

		CoroutineScope(Dispatchers.IO).launch {
			val response = service.searchByKeyword(
				q = q,
				maxResults = maxResults,
				type = "video"
			)

			withContext(Dispatchers.Main) {
				if (response.isSuccessful) {
					_searchVideoState.value = _searchVideoState.value?.copy(
						dataList = response.body()?.items ?: emptyList(),
						isLoading = false,
						isError = false,
						errorCode = null
					)
				} else {
					_searchVideoState.value = _searchVideoState.value?.copy(
						dataList = emptyList(),
						isLoading = false,
						isError = true,
						errorCode = response.code()
					)
				}
			}
		}
	}

	fun getChannelsByCategoryName(categoryName: String, maxResults: Int = 5) {
		_channelsByCategoryId.value = _channelsByCategoryId.value?.copy(
			dataList = emptyList(),
			isLoading = true,
			isError = false,
			errorCode = null
		)

		CoroutineScope(Dispatchers.IO).launch {
			val response = service.getChannelsByCategoryName(
				q = categoryName,
				maxResults = maxResults,
				type = "channel"
			)

			withContext(Dispatchers.Main) {
				if (response.isSuccessful) {
					_channelsByCategoryId.value = _channelsByCategoryId.value?.copy(
						dataList = response.body()?.items ?: emptyList(),
						isLoading = false,
						isError = false,
						errorCode = null
					)
				} else {
					_channelsByCategoryId.value = _channelsByCategoryId.value?.copy(
						dataList = emptyList(),
						isLoading = false,
						isError = true,
						errorCode = response.code()
					)
				}
			}
		}
	}
}