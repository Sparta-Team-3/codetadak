package com.goodness.codetadak.api.responses

data class ChannelsResponse(
	val kind: String,
	val etag: String,
	val items: List<ChannelItem>
)

data class ChannelItem(
	val kind: String,
	val etag: String,
	val id: String,
	val snippet: ChannelSnippet
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
	val width: Long,
	val height: Long
)
