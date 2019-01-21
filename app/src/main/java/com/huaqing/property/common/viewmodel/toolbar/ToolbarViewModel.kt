package com.huaqing.property.common.viewmodel.toolbar

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import com.huaqing.property.base.viewmodel.BaseViewModel

class ToolbarViewModel : BaseViewModel(), IToolbarDelegate {
    val toolbarName: MutableLiveData<String> = MutableLiveData()

    fun setSupportBar(activity: AppCompatActivity, toolbar: Toolbar, title: String) {
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar!!.setTitle("")
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        activity.supportActionBar!!.setHomeButtonEnabled(true); //设置返回键可用
        toolbar.setNavigationOnClickListener {
            activity.finish()
        }

        toolbarName.postValue(title)
    }

    companion object {
        fun instance() = ToolbarViewModel()
    }
}