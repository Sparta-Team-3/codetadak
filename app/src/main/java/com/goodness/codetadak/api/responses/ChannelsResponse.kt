package com.goodness.codetadak.api.responses

data class ChannelsResponse(
	val kind: String,
	val etag: String,
	val nextPageToken: String,
	val regionCode: String,
	val pageInfo: ChannelPageInfo,
	val items: List<ChannelItem>
)

data class ChannelItem(
	val kind: String,
	val etag: String,
	val id: ChannelId,
	val snippet: ChannelSnippet
)

data class ChannelId(
	val kind: String,
	val channelId: String
)

data class ChannelSnippet(
	val publishedAt: String,
	val channelId: String,
	val title: String,
	val description: String,
	val thumbnails: ChannelThumbnails,
	val channelTitle: String,
	val liveBroadcastContent: String,
	val publishTime: String
)

data class ChannelThumbnails(
	val default: ChannelDefault,
	val medium: ChannelDefault,
	val high: ChannelDefault
)

data class ChannelDefault(
	val url: String,
	val th: Long,
	val height: Long
)

data class ChannelPageInfo(
	val totalResults: Long,
	val resultsPerPage: Long
)

