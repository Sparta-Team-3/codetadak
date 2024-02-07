package com.goodness.codetadak.edtitprofiledialog

import androidx.annotation.StringRes
import com.goodness.codetadak.R

enum class EditMyProfileErrorMessage(@StringRes val message: Int) {
    EMPTY_NAME(R.string.empty_name_error),
    EMPTY_INFO(R.string.empty_info_error)
}