package cn.magic.compose_demo.window

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import cn.magic.compose_demo.R
import cn.magic.compose_demo.window.fatory.FloatWindowFactory
import cn.magic.compose_demo.window.utils.isFloatWindowOpAllowed
import kotlinx.android.synthetic.main.activity_float_window.*


/**
 * User: linsixu@yy.com
 * Date: 2021/1/8
 * Desc: 描述
 * SinceVer: TODO:哪个版本添加的
 */
class FloatWindowActivity : AppCompatActivity() {
    private val FLOAT_WINDOW_PERMISSION_CODE = 202
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_float_window)
        init()
    }

    private fun init() {
        val permissions = arrayOf(Manifest.permission.SYSTEM_ALERT_WINDOW)
        show_float_window.setOnClickListener {
            //我的华为手机下面获取权系统限弹窗会闪退
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(permissions,FLOAT_WINDOW_PERMISSION_CODE)
//            }
            if (!isFloatWindowOpAllowed(this)) {
                requestOverlayPermission()
            } else {
                FloatWindowFactory.getInstance(this).showFloatWindow()
            }
        }

        close_float_window.setOnClickListener {
            FloatWindowFactory.getInstance(this).closeFloatWindow()
        }
    }

    /***
     * 跳转到设置页面
     */
    private fun requestOverlayPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
        myIntent.data = Uri.parse("package:$packageName")
        startActivityForResult(myIntent, FLOAT_WINDOW_PERMISSION_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FLOAT_WINDOW_PERMISSION_CODE && resultCode == PackageManager.PERMISSION_GRANTED) {
            FloatWindowFactory.getInstance(this).showFloatWindow()
        }
    }
}