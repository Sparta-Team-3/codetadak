package com.goodness.codetadak.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object YouTubeInstance {
	private const val BASE_URL = "https://www.googleapis.com/youtube/v3"

	// TODO: YOUTUBE KEY 추가
	private const val API_KEY = "추가해주세요"

	private val client = OkHttpClient.Builder()
		.addInterceptor { chain ->
			val newRequest = chain.request().newBuilder()
				.header("Authorization", "Bearer $API_KEY")
				.build()
			chain.proceed(newRequest)
		}.build()

	private val retrofit: Retrofit = Retrofit
		.Builder()
		.baseUrl(BASE_URL)
		.addConverterFactory(GsonConverterFactory.create())
		.client(client)
		.build()

	fun getService(): YoutubeService {
		return retrofit.create(YoutubeService::class.java)
	}
}