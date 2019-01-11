package com.huaqing.property.ui.login

import com.huaqing.property.ui.MainActivity

class LoginNavigator(
    private val activity: androidx.fragment.app.FragmentActivity
) {

    fun toMain() = MainActivity.launch(activity)
}