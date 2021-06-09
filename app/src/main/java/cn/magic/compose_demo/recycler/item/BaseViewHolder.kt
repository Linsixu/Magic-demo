package cn.magic.compose_demo.recycler.item

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * User: linsixu@yy.com
 * Date: 2021/6/8
 * Desc: 描述
 * SinceVer: TODO:哪个版本添加的
 */
abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(t: T)
}