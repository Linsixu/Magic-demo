package cn.magic.compose_demo.window

import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes

/**
 * User: linsixu@yy.com
 * Date: 2021/1/8
 * Desc: 提供浮窗内容
 * SinceVer: 1.0.0
 */
interface IFloatWindowContentProvider {
//    /**
//     * 获取浮窗当前内容
//     */
//    fun getShowContent(): View

    /**
     * 通过布局id加载浮窗内容布局
     * @return LayoutRes
     */
    @LayoutRes
    fun getLayoutId(): Int

    /**
     * 通过View直接进行添加做为浮窗可见视图
     */
    fun getContent(): View?
}