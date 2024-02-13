package com.goodness.codetadak.api

import com.goodness.codetadak.api.responses.CategoriesResponse
import com.goodness.codetadak.api.responses.ChannelsResponse
import com.goodness.codetadak.api.responses.SearchResponse
import com.goodness.codetadak.api.responses.VideosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeService {
	@GET("youtube/v3/videos")
	suspend fun getVideos(
		@Query("part") part: String = "snippet",
		@Query("chart") chart: String = "mostPopular",
		@Query("maxResults") maxResults: Int = 5,
		@Query("videoCategoryId") videoCategoryId: Int = 0,
		@Query("regionCode") regionCode: String? = null,
	): Response<VideosResponse>

	@GET("youtube/v3/videos")
	suspend fun getVideo(
		@Query("part") part: String = "snippet",
		@Query("id") id: String
	): Response<VideosResponse>

	@GET("youtube/v3/videoCategories")
	suspend fun getVideoCategories(
		@Query("part") part: String = "snippet",
		@Query("regionCode") regionCode: String,
		@Query("hl") hl: String
	): Response<CategoriesResponse>

	@GET("youtube/v3/channels")
	suspend fun getChannel(
		@Query("part") part: String = "snippet",
		@Query("id") id: String
	): Response<ChannelsResponse>

	@GET("youtube/v3/search")
	suspend fun searchByKeyword(
		@Query("part") part: String = "snippet",
		@Query("maxResults") maxResults: Int = 5,
		@Query("order") order: String = "relevance",
		@Query("type") type: String = "video",
		@Query("regionCode") regionCode: String = "KR",
		@Query("q") q: String
	): Response<SearchResponse>

	@GET("youtube/v3/search")
	suspend fun getChannelsByCategoryName(
		@Query("part") part: String = "snippet",
		@Query("maxResults") maxResults: Int = 5,
		@Query("order") order: String = "relevance",
		@Query("type") type: String = "channel",
		@Query("regionCode") regionCode: String = "KR",
		@Query("q") q: String
	): Response<ChannelsResponse>
}