package com.huaqing.property.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.huaqing.property.R
import com.huaqing.property.databinding.ItemIndexLayoutBinding
import me.yokeyword.indexablerv.IndexableAdapter
import me.yokeyword.indexablerv.IndexableEntity

class BaseIndexableAdapter<T : IndexableEntity, DB : ViewDataBinding>(
    private val layoutId: Int,
    private val bindBinding: (View) -> DB,
    private val callback: (T, DB) -> Unit = { _, _ -> }
) : IndexableAdapter<T>() {
    override fun onCreateTitleViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        return IndexVH<String, ItemIndexLayoutBinding>(
            LayoutInflater.from(parent!!.context).inflate(R.layout.item_index_layout, parent, false),
            bindBinding = {
                ItemIndexLayoutBinding.bind(it)
            },
            callback = { indexTitle, binding ->
                binding.data = indexTitle
            })

    }


    override fun onBindTitleViewHolder(holder: RecyclerView.ViewHolder?, indexTitle: String?) {
        val vh = holder as IndexVH<String, *>
        vh.bind(indexTitle!!)
    }

    override fun onCreateContentViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder =
        ContentVH(
            LayoutInflater.from(parent!!.context).inflate(layoutId, parent, false),
            bindBinding,
            callback
        )

    override fun onBindContentViewHolder(holder: RecyclerView.ViewHolder?, t: T?) {
        val vh = holder as ContentVH<T, DB>
        vh.bind(t!!)
    }

    private inner class IndexVH<T, DB>(
        view: View,
        bindBinding: (View) -> DB,
        private val callback: (T, DB) -> Unit = { _, _ -> }
    ) : RecyclerView.ViewHolder(view) {

        private var binding: DB? = null

        init {
            binding = bindBinding(view)
        }

        fun bind(data: T) {
            callback(data, binding!!)
        }

    }

    class ContentVH<T : Any, DB : ViewDataBinding>(
        view: View,
        bindBinding: (View) -> DB,
        private val callback: (T, DB) -> Unit = { _, _ -> }
    ) : RecyclerView.ViewHolder(view) {

        private var binding: DB? = null

        init {
            binding = bindBinding(view)
        }

        fun bind(data: T) {
            callback(data, binding!!)
        }

    }
}
