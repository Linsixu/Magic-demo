package cn.magic.compose_demo.window

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.magic.compose_demo.R
import kotlinx.android.synthetic.main.activity_float_window.*

/**
 * User: linsixu@yy.com
 * Date: 2021/1/8
 * Desc: 描述
 * SinceVer: TODO:哪个版本添加的
 */
class FloatWindowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_float_window)
        init()
    }

    private fun init() {
        show_float_window.setOnClickListener {

        }

        close_float_window.setOnClickListener {

        }
    }
}