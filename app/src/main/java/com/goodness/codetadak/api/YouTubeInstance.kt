package com.goodness.codetadak.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object YouTubeInstance {
	val BASE_URL = "https://www.googleapis.com/youtube/v3"

	val client = Retrofit
		.Builder()
		.baseUrl(BASE_URL)
		.addConverterFactory(GsonConverterFactory.create())
		.build()

	fun getService(): YoutubeService {
		return client.create(YoutubeService::class.java)
	}
}