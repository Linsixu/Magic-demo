package cn.magic.compose_demo.window

import android.content.Context
import android.view.LayoutInflater
import android.view.View

/**
 * User: linsixu@yy.com
 * Date: 2021/1/8
 * Desc: 描述
 * SinceVer: TODO:哪个版本添加的
 */
class DefaultContentProvider : IFloatWindowContentProvider {
    private var mContent: View? = null
    override fun getContent(): View? {
        return mContent
    }

    override fun inflateContent(context: Context, layoutId: Int) {
        mContent = LayoutInflater.from(context.applicationContext).inflate(layoutId, null, false)
    }

    override fun addView(content: View) {
        mContent = content
    }
}