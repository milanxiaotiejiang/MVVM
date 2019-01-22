package com.huaqing.property.ui.workorder

import android.view.View
import android.widget.TextView
import com.huaqing.property.R
import com.huaqing.property.model.MessageData
import com.huaqing.property.model.WorkOrderData

object WorkUtils {

    @JvmStatic
    fun eventTain(data: WorkOrderData): CharSequence {
        when (data.type) {
            "0" -> return "公共维修"
            "1" -> return "居家维修"
            "2" -> return "保洁环卫"
            "3" -> return "安保巡检"
            else -> return ""
        }
    }

    @JvmStatic
    fun eventTainStatus(view: TextView, data: WorkOrderData): CharSequence {
        when (data.status) {
            "2" -> {
                view.setTextColor(view.getResources().getColor(R.color.accomplish_font))
                return "已完成"
            }
            "10" -> {
                view.setTextColor(view.getResources().getColor(R.color.to_accept_font))
                return "待受理"
            }
            "11" -> {
                view.setTextColor(view.getResources().getColor(R.color.accepted_font))
                return "已受理"
            }
            else -> return "未知"
        }
    }

    @JvmStatic
    fun eventNotarize(view: TextView, data: WorkOrderData): CharSequence {
        when (data.status) {
            "2" -> {
                view.visibility = View.GONE
                return "接受"
            }
            "10" -> {
                view.visibility = View.VISIBLE
                return "接受"
            }
            "11" -> {
                view.visibility = View.VISIBLE
                return "待上报"
            }
            else -> {
                view.visibility = View.GONE
                return "接受"
            }
        }
    }

    @JvmStatic
    fun eventLevel(view: TextView, data: WorkOrderData) {
        when (data.urgentLevel) {
            "0" -> {
                view.setText("普通")
                view.setTextColor(view.getResources().getColor(R.color.ordinary_font))
                view.setBackgroundResource(R.drawable.shape_ordinary_style)
            }
            "1" -> {
                view.setText("严重")
                view.setTextColor(view.getResources().getColor(R.color.urgency_font))
                view.setBackgroundResource(R.drawable.shape_severity_styles)
            }
            else -> {
                view.setText("紧急")
                view.setTextColor(view.getResources().getColor(R.color.severity_font))
                view.setBackgroundResource(R.drawable.shape_urgency_styles)
            }
        }
    }
}