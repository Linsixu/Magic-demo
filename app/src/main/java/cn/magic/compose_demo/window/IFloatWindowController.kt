package cn.magic.compose_demo.window

/**
 * User: linsixu@yy.com
 * Date: 2021/1/8
 * Desc: 浮窗操控类
 * SinceVer: 1.0.0
 */
interface IFloatWindowController {
    /**
     * 展示弹窗
     */
    fun show()

    /**
     * 隐藏弹窗
     */
    fun hide()


    /**
     * 创建浮窗的内容提供器
     * @param provider: 浮窗内容提供器接口
     */
    fun setProvider(provider: IFloatWindowContentProvider)

    /**
     * 重新刷新View
     */
    fun invalidView(provider: IFloatWindowContentProvider)

    /**
     * 更新浮窗的位置
     */
    fun updatePosition(x: Int, y: Int)
}