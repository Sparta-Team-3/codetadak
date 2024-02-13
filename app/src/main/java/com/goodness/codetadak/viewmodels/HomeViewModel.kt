package com.goodness.codetadak.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goodness.codetadak.api.YouTubeInstance
import com.goodness.codetadak.api.responses.CategoriesResponse
import com.goodness.codetadak.api.responses.CategoryItem
import com.goodness.codetadak.api.responses.ChannelsResponse
import com.goodness.codetadak.api.responses.VideoItem
import com.goodness.codetadak.api.responses.VideosResponse
import kotlinx.coroutines.launch

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
                Log.d("API Response1", "getVideoCategories Response: ${response.body()?.toString()}")
            } else {
                // API 호출 실패한 경우에 대한 처리를 여기에 작성/**/
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
                val categoriesWithVideos = mutableListOf<CategoryItem>()
                response.body()?.items?.forEach { category ->
                    val videosResponse = service.getVideos("snippet,contentDetails,statistics", "mostPopular", 5, category.id, regionCode)
                    if (videosResponse.isSuccessful && videosResponse.body()?.items?.isNotEmpty() == true) {
                        categoriesWithVideos.add(category)
                    }
                }
                videoCategoriesResponse.value = CategoriesResponse(response.body()?.kind ?: "", response.body()?.etag ?: "", categoriesWithVideos)
                Log.d("API Response2", "getVideoCategories Response: ${response.body()?.toString()}")
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
                Log.d("API Response3", "getVideosByCategory Response: ${response.body()?.toString()}")

                // items[0].id 필드의 값이 문자열인지 확인
                response.body()?.items?.get(0)?.id?.let { id ->
                    Log.d("API Check", "items[0].id is a ${id::class.java.simpleName}")
                }
            } else {
                Log.e("API Error", "Failed to get videos by category: ${response.errorBody()?.string()}")
            }
        }
    }

    // 특정 카테고리에 속하는 채널 정보 조회
     fun getChannelInfo(channelId: String) {
        viewModelScope.launch {
            val service = YouTubeInstance.getService()
            val response = service.getChannel("snippet", channelId)

            if (response.isSuccessful) {
                channelResponse.value = response.body()
                Log.d("API Response4", "getChannelInfo Response: ${response.body()?.toString()}")
            } else {
                Log.e("API Error", "Failed to get channel info: ${response.errorBody()?.string()}")
            }
        }
    }

    // 특정 카테고리에 속하는 비디오의 각 채널들의 정보 조회
    fun getChannelsByCategory(categoryId: Int, regionCode: String) {
        viewModelScope.launch {
            val service = YouTubeInstance.getService()
            val response = service.getVideos("snippet,contentDetails,statistics", "mostPopular", 5, categoryId, regionCode)

            if (response.isSuccessful) {
                response.body()?.items?.forEach { video ->
                    getChannelInfo(video.snippet.channelId)
                }
                Log.d("API Response5", "getChannelsByCategory Response: ${response.body()?.toString()}")
            } else {
                Log.e("API Error", "Failed to get videos by category: ${response.errorBody()?.string()}")
            }
        }
    }
}