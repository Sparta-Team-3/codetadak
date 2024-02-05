package com.goodness.codetadak.api

import com.goodness.codetadak.api.responses.VideosResponse
import retrofit2.Response
import retrofit2.http.GET

interface YoutubeService {
	@GET("videos")
	suspend fun getVideos(): Response<VideosResponse>
}