package com.goodness.codetadak.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goodness.codetadak.api.YouTubeInstance
import com.goodness.codetadak.api.responses.CategoriesResponse
import com.goodness.codetadak.api.responses.ChannelsResponse
import com.goodness.codetadak.api.responses.VideoItem
import com.goodness.codetadak.api.responses.VideosResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel : ViewModel() {
    val videosResponse: MutableLiveData<VideosResponse> = MutableLiveData()
    val videoCategoriesResponse: MutableLiveData<CategoriesResponse> = MutableLiveData()
    val videosByCategoryResponse: MutableLiveData<VideosResponse> = MutableLiveData()
    val videosBySelectedCategoryResponse: MutableLiveData<VideosResponse> = MutableLiveData()
    val channelResponse: MutableLiveData<ChannelsResponse> = MutableLiveData()
    val selectedVideo: MutableLiveData<VideoItem> = MutableLiveData()

    // 가장 많이 본 영상 목록 조회
    fun getMostPopularVideos() {
        viewModelScope.launch {
            val service = YouTubeInstance.getService()
            val response = service.getVideos()

            if (response.isSuccessful) {
                videosResponse.value = response.body()
            } else {
                // API 호출 실패한 경우에 대한 처리를 여기에 작성
                Log.e("API Error", "Failed to get most popular videos: ${response.errorBody()?.string()}")
            }
        }
    }

    // 비디오 카테고리 조회
    fun getVideoCategories(regionCode: String, hl: String) {
        viewModelScope.launch {
            val service = YouTubeInstance.getService()
            val response = service.getVideoCategories("snippet", regionCode, hl)

            if (response.isSuccessful) {
                val categoriesWithVideos = response.body()?.items?.map { category ->
                    async {
                        val videosResponse = service.getVideos("snippet,contentDetails,statistics", "mostPopular", 5, category.id, regionCode)
                        if (videosResponse.isSuccessful && videosResponse.body()?.items?.isNotEmpty() == true) {
                            category
                        } else {
                            null
                        }
                    }
                }?.awaitAll()?.filterNotNull()
                videoCategoriesResponse.value = CategoriesResponse(response.body()?.kind ?: "", response.body()?.etag ?: "", categoriesWithVideos ?: emptyList())
            } else {
                Log.e("API Error", "Failed to get video categories: ${response.errorBody()?.string()}")
            }
        }
    }

    // 특정 카테고리에 속하는 인기 비디오 목록 조회
    fun getVideosByCategory(categoryId: Int, regionCode: String) {
        viewModelScope.launch {
            val service = YouTubeInstance.getService()
            val response = service.getVideos("snippet,contentDetails,statistics", "mostPopular", 5, categoryId, regionCode)

            if (response.isSuccessful) {
                videosByCategoryResponse.value = response.body()
            } else {
                Log.e("API Error", "Failed to get videos by category: ${response.errorBody()?.string()}")
            }
        }
    }

    // 특정 카테고리에 속하는 채널 정보 조회
    private suspend fun getChannelInfo(channelId: String): Response<ChannelsResponse> {
        val service = YouTubeInstance.getService()
        return service.getChannel("snippet", channelId, "ko")
    }

    fun getChannelsByCategory(categoryId: Int, regionCode: String) {
        viewModelScope.launch {
            val service = YouTubeInstance.getService()
            val response = service.getVideos("snippet,contentDetails,statistics", "mostPopular", 5, categoryId, regionCode)

            if (response.isSuccessful) {
                val channelResponses = response.body()?.items?.map { video ->
                    async { getChannelInfo(video.snippet.channelId) }
                }?.awaitAll()

                channelResponse.value = ChannelsResponse(
                    kind = channelResponses?.firstOrNull()?.body()?.kind ?: "",
                    etag = channelResponses?.firstOrNull()?.body()?.etag ?: "",
                    items = channelResponses?.mapNotNull { it.body()?.items?.firstOrNull() } ?: emptyList()
                )
            } else {
                Log.e("API Error", "Failed to get videos by category: ${response.errorBody()?.string()}")
            }
        }
    }
}