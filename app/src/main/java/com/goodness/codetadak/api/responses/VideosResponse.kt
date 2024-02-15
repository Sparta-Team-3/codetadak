package com.goodness.codetadak.api.responses

data class VideosResponse(
	val kind: String,
	val etag: String,
	val items: List<VideoItem>,
	val nextPageToken: String,
	val pageInfo: PageInfo
)

data class VideoItem(
	val kind: String,
	val etag: String,
	val id: String,
	val snippet: VideoSnippet
)

data class VideoSnippet(
	val publishedAt: String,
	val channelId: String,
	val title: String,
	val description: String,
	val thumbnails: Thumbnails,
	val channelTitle: String,
	val tags: List<String>? = null,
	val categoryId: String,
	val liveBroadcastContent: String,
	val defaultLanguage: String? = null,
	val localized: Localized,
	val defaultAudioLanguage: String? = null,
	var isFavorite: Boolean = false
)

data class Localized(
	val title: String,
	val description: String
)

data class Thumbnails(
	val default: Default,
	val medium: Default,
	val high: Default,
	val standard: Default,
	val maxres: Default? = null
)

data class Default(
	val url: String,
	val width: Long,
	val height: Long
)

data class PageInfo(
	val totalResults: Long,
	val resultsPerPage: Long
)