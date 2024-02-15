package com.goodness.codetadak.api.responses

data class CategoriesResponse(
	val kind: String,
	val etag: String,
	val items: List<CategoryItem>
)

data class CategoryItem(
	val kind: String,
	val etag: String,
	val id: Int,
	val snippet: CategorySnippet
)

data class CategorySnippet(
	val title: String,
	val assignable: Boolean,
	val channelId: String
)
