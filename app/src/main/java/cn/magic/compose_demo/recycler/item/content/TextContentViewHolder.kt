package cn.magic.compose_demo.recycler.item.content

import android.view.View
import android.widget.TextView
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
class TextContentViewHolder<T: UniteBean>(itemView: View) : BaseViewHolder<T>(itemView) {
    private val text = itemView.findViewById<TextView>(R.id.item_content)

    override fun bind(t: T) {
        (t as? TestBean)?.let {
            text.text = it.content
        }
    }
}