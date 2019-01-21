package com.huaqing.property.ui.main.home

import android.view.View
import android.widget.TextView
import com.huaqing.property.R
import com.huaqing.property.model.MessageData
import com.huaqing.property.view.CircleImageView

object HomeUtils {

    @JvmStatic
    fun eventCircle(circleImageView: CircleImageView, data: MessageData): Any {
        if (!data.type.isEmpty()) {
            when (data.type) {
                "0" -> return R.mipmap.ic_home_maintenance
                "1" -> return R.mipmap.ic_home_maintenance
                "2" -> return R.mipmap.ic_home_cleaning
                "3" -> return R.mipmap.ic_home_security
            }
        }
        return R.mipmap.ic_home_news
    }

    @JvmStatic
    fun eventTitle(data: MessageData): CharSequence {
        if (!data.type.isEmpty()) {
            when (data.type) {
                "0" -> return "公共维修"
                "1" -> return "居家维修"
                "2" -> return "保洁环卫"
                "3" -> return "安保巡检"
            }
        }
        return data.title
    }

    @JvmStatic
    fun eventContext(data: MessageData): CharSequence {
        if (!data.type.isEmpty()) {
            return data.description
        }
        return data.content
    }


    @JvmStatic
    fun eventUrgentLevel(view: TextView, data: MessageData): CharSequence {
        if (data.urgentLevel.equals("0")) {
            view.setTextColor(view.getResources().getColor(R.color.ordinary_font))
            view.setBackgroundResource(R.drawable.shape_ordinary_style)
            return "一般"
        } else if (data.urgentLevel.equals("1")) {

            view.setTextColor(view.getResources().getColor(R.color.urgency_font))
            view.setBackgroundResource(R.drawable.shape_urgency_styles)
            return "严重"
        } else if (data.urgentLevel.equals("2")) {
            view.setTextColor(view.getResources().getColor(R.color.severity_font))
            view.setBackgroundResource(R.drawable.shape_severity_styles)
            return "紧急"
        }
        view.visibility = View.GONE
        return ""
    }
}