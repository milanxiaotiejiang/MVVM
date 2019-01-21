package com.huaqing.property.ui.workorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.huaqing.property.R

class WorkOrderFragment : Fragment() {

    var status: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        status = arguments!!.getString(STATUS)
    }

    var mRootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mRootView = LayoutInflater.from(context).inflate(R.layout.fragment_work, container, false)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRootView!!.findViewById<TextView>(R.id.tv).text = status
    }

    companion object {
        var STATUS: String = "status"

        fun instance(status: String): WorkOrderFragment {
            val fragment = WorkOrderFragment()
            val bundle = Bundle()
            bundle.putString(STATUS, status)
            fragment.arguments = bundle
            return fragment
        }
    }
}