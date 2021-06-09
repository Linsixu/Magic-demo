package cn.magic.compose_demo.recycler.bean

/**
 * User: linsixu@yy.com
 * Date: 2021/6/8
 * Desc: 描述
 * SinceVer: TODO:哪个版本添加的
 */
class TestBean(
    val content: String
) : UniteBean {

    override fun getType(): Int {
        return 1
    }
}