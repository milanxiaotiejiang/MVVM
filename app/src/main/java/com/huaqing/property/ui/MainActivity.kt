package com.huaqing.property.ui

import android.content.Intent
import com.huaqing.property.R
import com.huaqing.property.base.ui.BaseActivity
import com.huaqing.property.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityDelegate>() {
    override val layoutId = R.layout.activity_main

    override val viewDelegate = MainActivityDelegate()

    override fun initView() {
        mBinding.delegate = viewDelegate
    }

    companion object {

        fun launch(activity: androidx.fragment.app.FragmentActivity) =
            activity.apply {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
    }
}