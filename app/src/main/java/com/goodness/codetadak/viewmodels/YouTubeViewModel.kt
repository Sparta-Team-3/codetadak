package com.goodness.codetadak.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goodness.codetadak.api.YouTubeInstance
import com.goodness.codetadak.api.responses.CategoryItem
import com.goodness.codetadak.api.responses.SearchItem
import com.goodness.codetadak.api.responses.VideoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class DataState<T>(
	val dataList: List<T> = emptyList(),
	val isLoading: Boolean = false,
	val isError: Boolean = false
)

class YoutubeViewModel : ViewModel() {
	private val service = YouTubeInstance.getService()

	private var _mostPopularVideoState: MutableLiveData<DataState<VideoItem>> = MutableLiveData(DataState())
	private var _categoryState: MutableLiveData<DataState<CategoryItem>> = MutableLiveData(DataState())
	private var _mostPopularVideosByCategoryState: MutableLiveData<DataState<VideoItem>> = MutableLiveData(DataState())
	private var _searchVideoState: MutableLiveData<DataState<SearchItem>> = MutableLiveData(DataState())

	val mostPopularVideoState: LiveData<DataState<VideoItem>>
		get() = _mostPopularVideoState
	val categoryState: LiveData<DataState<CategoryItem>>
		get() = _categoryState
	val mostPopularVideosByCategoryState: LiveData<DataState<VideoItem>>
		get() = _mostPopularVideosByCategoryState
	val searchVideoState: LiveData<DataState<SearchItem>>
		get() = _searchVideoState

	fun getMostPopularVideos(maxResults: Int = 5) {
		_mostPopularVideoState.value = _mostPopularVideoState.value?.copy(
			dataList = emptyList(),
			isLoading = true,
			isError = false
		)

		CoroutineScope(Dispatchers.IO).launch {
			val response = service.getVideos(
				maxResults = maxResults
			)

			if (response.isSuccessful) {
				withContext(Dispatchers.Main) {
					_mostPopularVideoState.value = _mostPopularVideoState.value?.copy(
						dataList = response.body()?.items ?: emptyList(),
						isLoading = false,
						isError = false
					)
				}
			} else {
				_mostPopularVideoState.value = _mostPopularVideoState.value?.copy(
					dataList = emptyList(),
					isLoading = false,
					isError = true
				)
			}
		}
	}

	fun getCategories(regionCode: String, hl: String) {
		_categoryState.value = _categoryState.value?.copy(
			dataList = emptyList(),
			isLoading = true,
			isError = false
		)

		CoroutineScope(Dispatchers.IO).launch {
			val response = service.getVideoCategories(
				regionCode = regionCode,
				hl = hl
			)

			if (response.isSuccessful) {
				withContext(Dispatchers.Main) {
					_categoryState.value = _categoryState.value?.copy(
						dataList = response.body()?.items ?: emptyList(),
						isLoading = false,
						isError = false
					)
				}
			} else {
				_categoryState.value = _categoryState.value?.copy(
					dataList = emptyList(),
					isLoading = false,
					isError = true
				)
			}
		}
	}

	fun getSearchByKeyword(q: String, maxResults: Int = 5) {
		_searchVideoState.value = _searchVideoState.value?.copy(
			dataList = emptyList(),
			isLoading = true,
			isError = false
		)

		CoroutineScope(Dispatchers.IO).launch {
			val response = service.searchByKeyword(
				q = q,
				maxResults = maxResults
			)

			if (response.isSuccessful) {
				withContext(Dispatchers.Main) {
					_searchVideoState.value = _searchVideoState.value?.copy(
						dataList = response.body()?.items ?: emptyList(),
						isLoading = false,
						isError = false
					)
				}
			} else {
				_searchVideoState.value = _searchVideoState.value?.copy(
					dataList = emptyList(),
					isLoading = false,
					isError = true
				)
			}
		}
	}
}