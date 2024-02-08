package com.goodness.codetadak.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goodness.codetadak.api.responses.LanguageItem

class LanguageViewModel : ViewModel() {
	private var _languageList: MutableLiveData<List<LanguageItem>> = MutableLiveData(
		listOf(
			LanguageItem(
				"C",
				value = "Programming C"
			),
			LanguageItem("Kotlin"),
			LanguageItem("Python"),
			LanguageItem("JavaScript"),
			LanguageItem("TypeScript"),
		)
	)

	val languageList: LiveData<List<LanguageItem>>
		get() = _languageList

	fun updateCurLanguage(language: LanguageItem) {
		_languageList.value = _languageList.value?.map {
			it.copy(
				isSelected = (it.name == language.name)
			)
		}
	}
}