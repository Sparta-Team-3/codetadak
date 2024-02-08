package com.goodness.codetadak.api

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object YouTubeInstance {
	private const val BASE_URL = "https://www.googleapis.com/"

	// TODO: YOUTUBE KEY 추가, 헤더아님!!
	private const val API_KEY = ""

	private val client = OkHttpClient.Builder()
		.addInterceptor { chain ->
			val originalRequest: Request = chain.request()
			val originalHttpUrl: HttpUrl = originalRequest.url()

			val url: HttpUrl = originalHttpUrl.newBuilder()
				.addQueryParameter("key", API_KEY)
				.build()

			val newRequest: Request = originalRequest.newBuilder()
				.url(url)
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