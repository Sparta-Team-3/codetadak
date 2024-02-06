package com.goodness.codetadak.api

import com.goodness.codetadak.api.responses.CategoriesResponse
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
		@Query("videoCategoryId") videoCategoryId: Int = 0
	): Response<VideosResponse>

	@GET("youtube/v3/videoCategories")
	suspend fun getVideoCategories(
		@Query("part") part: String = "snippet",
		@Query("regionCode") regionCode: String,
		@Query("hl") hl: String
	): Response<CategoriesResponse>

	// TODO: 채널 카테고리 검색 안됨?
	@GET("youtube/v3/channels")
	suspend fun getChannels(
		@Query("part") part: String = "snippet",
		@Query("id") id: String
	)

	@GET("youtube/v3/search")
	suspend fun searchByKeyword(
		@Query("part") part: String = "snippet",
		@Query("maxResults") maxResults: Int = 5,
		@Query("order") order: String = "relevance",
		@Query("q") q: String
	): Response<SearchResponse>
}