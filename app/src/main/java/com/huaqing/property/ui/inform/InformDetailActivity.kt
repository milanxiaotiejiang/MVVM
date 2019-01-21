package com.huaqing.property.ui.inform

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.huaqing.property.R
import com.huaqing.property.base.ui.activity.BaseActivity
import com.huaqing.property.common.viewmodel.toolbar.ToolbarViewModel
import com.huaqing.property.databinding.ActivityInformDetailBinding
import kotlinx.android.synthetic.main.toolbar.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class InformDetailActivity : BaseActivity<ActivityInformDetailBinding>() {

    override val kodein = Kodein.lazy {
        extend(parentKodein)
        import(informDetailKodeinModule)
    }

    val viewModel: InformDetailViewModel by instance()
    val toolbarViewModel: ToolbarViewModel by instance()

    override val layoutId: Int = R.layout.activity_inform_detail


    override fun initView() {

        toolbarViewModel.setSupportBar(this, tool_bar, "消息详情")

        val title = intent.getStringExtra(TITLE)
        val date = intent.getStringExtra(DATE)
        val content = intent.getStringExtra(CONTENT)

        viewModel.title.postValue(title)
        viewModel.date.postValue(date)
        viewModel.content.postValue(content)

    }

    companion object {

        var TITLE: String = "title"
        var DATE: String = "date"
        var CONTENT: String = "content"

        fun launch(activity: FragmentActivity, fragment: Fragment, title: String, content: String, date: String) {
            val intent = Intent(activity, InformDetailActivity::class.java)
            intent.putExtra(TITLE, title)
            intent.putExtra(CONTENT, content)
            intent.putExtra(DATE, date)
            fragment.startActivity(intent)
        }
    }
}