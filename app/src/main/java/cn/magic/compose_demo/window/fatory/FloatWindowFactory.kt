package cn.magic.compose_demo.window.fatory

import android.content.Context
import cn.magic.compose_demo.window.DefaultContentProvider
import cn.magic.compose_demo.window.FloatWindowController
import cn.magic.compose_demo.window.IFloatWindowContentProvider
import cn.magic.compose_demo.window.IFloatWindowController

/**
 * User: linsixu@yy.com
 * Date: 2021/1/8
 * Desc: 浮窗工厂
 * SinceVer: 1.0.0
 */
class FloatWindowFactory {
    companion object {
        private var mFactory: FloatWindowFactory? = null
        private var mFloatWindowController: IFloatWindowController? = null

        fun getInstance(
            context: Context,
            provider: IFloatWindowContentProvider?
        ): FloatWindowFactory {
            if (mFactory == null) {
                synchronized(this) {
                    if (mFactory == null) {
                        mFactory = FloatWindowFactory()
                        mFloatWindowController =
                            FloatWindowController(context.applicationContext).apply {
                                val currentProvider = provider ?: DefaultContentProvider()
                                this.setProvider(currentProvider)
                            }
                    }
                }
            }
            return mFactory!!
        }
    }

    /**
     * 开启浮窗
     */
    fun showFloatWindow() {
        mFloatWindowController?.show()
    }

    /**
     * 关闭浮窗
     */
    fun closeFloatWindow() {
        mFloatWindowController?.hide()
    }

    /**
     * 重新设置浮窗的内容
     */
    fun switchContentProvider(provider: IFloatWindowContentProvider): FloatWindowFactory {
        mFloatWindowController?.setProvider(provider)
        return this
    }

    fun replaceContent() {

    }
}