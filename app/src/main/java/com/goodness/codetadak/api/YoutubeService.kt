package com.goodness.codetadak.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YoutubeService {
	@GET("videos")
	suspend fun getVideos(): Response<VideosResponse>
}