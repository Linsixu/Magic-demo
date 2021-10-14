import javassist.ClassPool
import org.junit.Test


/**
 * User: linsixu@yy.com
 * Date: 2021/10/8
 * Desc: 描述
 * SinceVer: TODO:哪个版本添加的
 */
class Hello {
    fun say() {
        println("Hello")
    }
}


class MyTest {

    @Test
    fun main() {
        val cp = ClassPool.getDefault()
        val cc = cp.get("Hello")
        val method = cc.getDeclaredMethod("say")
        method.insertBefore("{ System.out.println(\"Hello.say():\"); }")
        val c = cc.toClass()
        val h = c.newInstance() as? Hello
        h?.say()
    }
}