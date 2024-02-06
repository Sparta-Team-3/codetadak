package com.goodness.codetadak

import androidx.annotation.StringRes

enum class EditMyProfileErrorMessage(@StringRes val message: Int) {
    EMPTY_NAME(R.string.empty_name_error),
    EMPTY_INFO(R.string.empty_info_error)
}