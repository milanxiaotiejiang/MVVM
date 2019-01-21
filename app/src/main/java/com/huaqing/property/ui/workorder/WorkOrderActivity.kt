package com.huaqing.property.ui.workorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class WorkOrderActivity : AppCompatActivity() {


    companion object {

        fun launch(activity: FragmentActivity, fragment: Fragment) =
            activity.apply {
                startActivity(Intent(this, WorkOrderActivity::class.java))
                finish()
            }
    }
}