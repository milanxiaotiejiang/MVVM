package com.huaqing.property.common.helper

import android.content.SharedPreferences
import com.huaqing.property.utils.prefs.boolean
import com.huaqing.property.utils.prefs.string

class PrefsHelper(prefs: SharedPreferences) {

    var autoLogin by prefs.boolean("autoLogin", true)

    var token by prefs.string("token", "")
}