package com.huaqing.property.utils

import com.huaqing.property.base.App
import com.huaqing.property.ext.toast

inline fun toast(value: () -> String): Unit =
    App.INSTANCE.toast(value)