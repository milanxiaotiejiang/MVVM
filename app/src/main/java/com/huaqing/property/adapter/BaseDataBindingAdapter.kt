package com.huaqing.property.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.huaqing.property.binding.DEFAULT_THROTTLE_TIME
import com.huaqing.property.binding.setOnClickEvent
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit

class BaseDataBindingAdapter<T : Any, DB : ViewDataBinding>(
    private val layoutId: Int,
    private val dataSource: () -> List<T>,
    private val bindBinding: (View) -> DB,
    private val callback: (T, DB, Int) -> Unit = { _, _, _ -> }
) : RecyclerView.Adapter<BaseDataBindingViewHolder<T, DB>>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): BaseDataBindingViewHolder<T, DB> {
        val baseViewHolder = BaseDataBindingViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false),
            bindBinding,
            callback
        )
//        bindViewClickListener(baseViewHolder)
        return baseViewHolder
    }

    override fun onBindViewHolder(holder: BaseDataBindingViewHolder<T, DB>, position: Int) =
        holder.bind(dataSource()[position], position)

    override fun getItemCount(): Int = dataSource().size

    fun forceUpdate() {
        notifyDataSetChanged()
    }


    private var mOnItemClickListener: OnItemClickListener? = null

    private fun bindViewClickListener(baseViewHolder: BaseDataBindingViewHolder<T, DB>) {
        val view = baseViewHolder.itemView
        view.setOnClickListener {
            setOnItemClick(it, baseViewHolder.layoutPosition)
        }
    }

    private fun getOnItemClickListener(): OnItemClickListener? {
        return mOnItemClickListener
    }

    private fun setOnItemClick(v: View, position: Int) {
        getOnItemClickListener()!!.onItemClick(v, position)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mOnItemClickListener = listener
    }
}


interface OnItemClickListener {

    fun onItemClick(view: View, position: Int)
}
