package cn.magic.compose_demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_target_test.*

/**
 * User: linsixu@yy.com
 * Date: 2021/4/19
 * Desc: 描述
 * SinceVer: TODO:哪个版本添加的
 */
class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target_test)

        click_finish.setOnClickListener {
            val intent = Intent()
            intent.putExtra("magic", "Hello world")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}