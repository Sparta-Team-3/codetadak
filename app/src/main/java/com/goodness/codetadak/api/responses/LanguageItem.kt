package com.goodness.codetadak.api.responses

data class LanguageItem(
	val name: String,
	val isSelected: Boolean = false,
	val value: String = name
)
