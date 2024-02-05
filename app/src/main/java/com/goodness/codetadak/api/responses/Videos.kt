package com.goodness.codetadak.api.responses

import java.util.*

data class VideosResponse(
	val kind: String,
	val etag: String,
	val id: String,
	val snippet: Snippet,
	val contentDetails: ContentDetails,
	val status: Status,
	val statistics: Statistics,
	val player: Player,
	val topicDetails: TopicDetails,
	val recordingDetails: RecordingDetails,
	val fileDetails: FileDetails,
	val processingDetails: ProcessingDetails,
	val suggestions: Suggestions,
	val liveStreamingDetails: LiveStreamingDetails,
	val localizations: Map<String, Localization>
)

data class Snippet(
	val publishedAt: Date,
	val channelId: String,
	val title: String,
	val description: String,
	val thumbnails: Map<String, Thumbnail>,
	val channelTitle: String,
	val tags: List<String>,
	val categoryId: String,
	val liveBroadcastContent: String,
	val defaultLanguage: String,
	val localized: Localized,
	val defaultAudioLanguage: String
)

data class Thumbnail(
	val url: String,
	val width: Int,
	val height: Int
)

data class Localized(
	val title: String,
	val description: String
)

data class ContentDetails(
	val duration: String,
	val dimension: String,
	val definition: String,
	val caption: String,
	val licensedContent: Boolean,
	val regionRestriction: RegionRestriction,
	val contentRating: ContentRating,
	val projection: String,
	val hasCustomThumbnail: Boolean
)

data class RegionRestriction(
	val allowed: List<String>,
	val blocked: List<String>
)

data class ContentRating(
	// 여러 국가의 등급에 대한 정보들이 들어갈 수 있습니다.
	// 필요에 따라 필드를 추가하거나 조정할 수 있습니다.
	// 예시로 "usRating"을 추가합니다.
	val usRating: String
)

data class Status(
	val uploadStatus: String,
	val failureReason: String,
	val rejectionReason: String,
	val privacyStatus: String,
	val publishAt: Date,
	val license: String,
	val embeddable: Boolean,
	val publicStatsViewable: Boolean,
	val madeForKids: Boolean,
	val selfDeclaredMadeForKids: Boolean
)

data class Statistics(
	val viewCount: String,
	val likeCount: String,
	val dislikeCount: String,
	val favoriteCount: String,
	val commentCount: String
)

data class Player(
	val embedHtml: String,
	val embedHeight: Long,
	val embedWidth: Long
)

data class TopicDetails(
	val topicIds: List<String>,
	val relevantTopicIds: List<String>,
	val topicCategories: List<String>
)

data class RecordingDetails(
	val recordingDate: Date
)

data class FileDetails(
	val fileName: String,
	val fileSize: Long,
	val fileType: String,
	val container: String,
	val videoStreams: List<VideoStream>,
	val audioStreams: List<AudioStream>,
	val durationMs: Long,
	val bitrateBps: Long,
	val creationTime: String
)

data class VideoStream(
	val widthPixels: Int,
	val heightPixels: Int,
	val frameRateFps: Double,
	val aspectRatio: Double,
	val codec: String,
	val bitrateBps: Long,
	val rotation: String,
	val vendor: String
)

data class AudioStream(
	val channelCount: Int,
	val codec: String,
	val bitrateBps: Long,
	val vendor: String
)

data class ProcessingDetails(
	val processingStatus: String,
	val processingProgress: ProcessingProgress,
	val processingFailureReason: String,
	val fileDetailsAvailability: String,
	val processingIssuesAvailability: String,
	val tagSuggestionsAvailability: String,
	val editorSuggestionsAvailability: String,
	val thumbnailsAvailability: String
)

data class ProcessingProgress(
	val partsTotal: Long,
	val partsProcessed: Long,
	val timeLeftMs: Long
)

data class Suggestions(
	val processingErrors: List<String>,
	val processingWarnings: List<String>,
	val processingHints: List<String>,
	val tagSuggestions: List<TagSuggestion>,
	val editorSuggestions: List<String>
)

data class TagSuggestion(
	val tag: String,
	val categoryRestricts: List<String>
)

data class LiveStreamingDetails(
	val actualStartTime: Date,
	val actualEndTime: Date,
	val scheduledStartTime: Date,
	val scheduledEndTime: Date,
	val concurrentViewers: Long,
	val activeLiveChatId: String
)

data class Localization(
	val title: String,
	val description: String
)

