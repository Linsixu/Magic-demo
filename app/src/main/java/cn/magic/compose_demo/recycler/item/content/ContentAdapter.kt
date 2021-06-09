package cn.magic.compose_demo.recycler.item.content

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.magic.compose_demo.R
import cn.magic.compose_demo.recycler.bean.TestBean
import cn.magic.compose_demo.recycler.bean.UniteBean
import cn.magic.compose_demo.recycler.item.BaseViewHolder

/**
 * User: linsixu@yy.com
 * Date: 2021/6/8
 * Desc: 描述
 * SinceVer: TODO:哪个版本添加的
 */
class ContentAdapter<T : UniteBean>(val content: List<T>) :
    RecyclerView.Adapter<BaseViewHolder<T>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return when (viewType) {
            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.iteam_content_view, parent, false)
                TextContentViewHolder<TestBean>(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.iteam_content_view, parent, false)
                TextContentViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return content.size
    }

    override fun getItemViewType(position: Int): Int {
        return content[position].getType()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {

    }

}

