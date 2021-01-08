package cn.magic.compose_demo.window.utils

import android.Manifest
import android.app.AppOpsManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Binder
import android.os.Build
import android.os.Process
import android.util.Log

private val TAG: String = "Permission"

/**
 * User: linsixu@yy.com
 * Date: 2021/1/8
 * Desc: 浮窗权限检测
 * SinceVer: 1.0.0
 */

fun isFloatWindowOpAllowed(context: Context): Boolean {
    val version = Build.VERSION.SDK_INT
    return if (version >= Build.VERSION_CODES.KITKAT) {
        checkOp(context)
    } else {
        context.applicationInfo.flags and 1 shl 27 == 1 shl 27
    }
}

fun checkOp(context: Context): Boolean {
    val version = Build.VERSION.SDK_INT
    if (version >= Build.VERSION_CODES.M) {
        val manager =
            context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val result: Int = safeCheck(manager, context)
        Log.e(
            TAG, String.format("checkOp:OPSTR_SYSTEM_ALERT_WINDOW,ret:%s", result.toString())
        )
        return if (result == AppOpsManager.MODE_DEFAULT) {
            (context.checkCallingOrSelfPermission(Manifest.permission.SYSTEM_ALERT_WINDOW)
                    == PackageManager.PERMISSION_GRANTED)
        } else result == AppOpsManager.MODE_ALLOWED
    } else if (version >= Build.VERSION_CODES.KITKAT) {
        val manager =
            context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        try {
            val method = manager.javaClass
                .getDeclaredMethod(
                    "checkOp",
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType,
                    String::class.java
                )
            val property = method.invoke(
                manager, 24,  // AppOpsManager.OP_SYSTEM_ALERT_WINDOW
                Binder.getCallingUid(), context.packageName
            ) as Int
            return AppOpsManager.MODE_ALLOWED == property
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    } else {
        Log.e(TAG, "Below API 19 cannot invoke!")
        return true
    }
    return false
}

private fun safeCheck(manager: AppOpsManager, context: Context): Int {
    return try {
        manager.checkOpNoThrow(
            AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW,
            Process.myUid(),
            context.packageName
        )
    } catch (e: java.lang.Exception) {
        Log.e(TAG, "safeCheck:", e)
        AppOpsManager.MODE_ERRORED
    }
}