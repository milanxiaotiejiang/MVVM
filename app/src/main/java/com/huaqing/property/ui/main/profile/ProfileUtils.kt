package com.huaqing.property.ui.main.profile

import com.huaqing.property.R
import com.huaqing.property.base.App

object ProfileUtils {

    @JvmStatic
    fun proIdToString(proId: Int?): String =
        App.INSTANCE.getString(proId!!)

}