package cn.magic.compose_demo.recycler.item.head

import android.view.View
import android.widget.TextView
import cn.magic.compose_demo.R
import cn.magic.compose_demo.recycler.item.BaseViewHolder

/**
 * User: linsixu@yy.com
 * Date: 2021/6/8
 * Desc: 描述
 * SinceVer: TODO:哪个版本添加的
 */
class HeaderViewHolder(val view: View) : BaseViewHolder<String>(view) {
    private val flowerNumberTextView: TextView = itemView.findViewById(R.id.item_number)

    override fun bind(number: String) {
        flowerNumberTextView.text = number
    }
}