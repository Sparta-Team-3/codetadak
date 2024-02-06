package com.goodness.codetadak.api.responses

data class SearchResponse(
	val kind: String,
	val etag: String,
	val nextPageToken: String,
	val regionCode: String,
	val pageInfo: SearchPageInfo,
	val items: List<SearchItem>
)

data class SearchItem(
	val kind: String,
	val etag: String,
	val id: ID,
	val snippet: Snippet
)

data class ID(
	val kind: String,
	val videoId: String
)

data class Snippet(
	val publishedAt: String,
	val channelId: String,
	val title: String,
	val description: String,
	val thumbnails: SearchThumbnails,
	val channelTitle: String,
	val liveBroadcastContent: String,
	val publishTime: String
)

data class SearchThumbnails(
	val default: SearchDefault,
	val medium: SearchDefault,
	val high: SearchDefault
)

data class SearchDefault(
	val url: String,
	val width: Long? = null,
	val height: Long? = null
)

data class SearchPageInfo(
	val totalResults: Long,
	val resultsPerPage: Long
)

