package com.goodness.codetadak.sharedpreferences

import android.net.Uri

data class UserInfo (var profileImage: Uri?, val name: String, val info: String) {
}