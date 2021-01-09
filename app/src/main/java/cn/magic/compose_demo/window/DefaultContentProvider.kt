package cn.magic.compose_demo.window

import android.view.View
import cn.magic.compose_demo.R

/**
 * User: linsixu@yy.com
 * Date: 2021/1/8
 * Desc: 默认画面
 * SinceVer: 1.0.0
 */
class DefaultContentProvider : IFloatWindowContentProvider {
    override fun getLayoutId(): Int {
        return R.layout.float_window_test
    }

    override fun getContent(): View? {
        return null
    }
}