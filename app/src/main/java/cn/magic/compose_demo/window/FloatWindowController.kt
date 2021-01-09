package cn.magic.compose_demo.window

import android.content.Context
import android.graphics.PixelFormat
import android.graphics.Point
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import cn.magic.compose_demo.window.utils.isFloatWindowOpAllowed
import java.util.concurrent.atomic.AtomicBoolean

/**
 * User: linsixu@yy.com
 * Date: 2021/1/8
 * Desc: 浮窗的控制器
 * SinceVer: 1.0.0
 */
const val DEFAULT_SHOW_X = 0

class FloatWindowController(private val context: Context) : IFloatWindowController {
    private val TAG: String = "FloatWindowController"
    private var mWindowManager: WindowManager =
        context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private lateinit var mProvider: IFloatWindowContentProvider
    private var mLayoutParams: WindowManager.LayoutParams? = null
    private var mCurrentView: View? = null
    private val mApplicationContext get() = context.applicationContext
    private val mHasAlreadyShow: AtomicBoolean = AtomicBoolean(false)

    override fun show() {
//        if (getCurrentShowContent() == null) throw IllegalArgumentException("还没初始化contentView")
        if (mHasAlreadyShow.get()) {
            Log.i(TAG, "已经出现悬浮窗口了")
            return
        }
        val point = Point()
        val phoneSize = mWindowManager.defaultDisplay.getSize(point)
        val params = getCurrentLayoutParams().apply {
            this.x = DEFAULT_SHOW_X
            this.y = point.y / 2
        }
        mWindowManager.addView(getCurrentShowContent(), getCurrentLayoutParams())
        mHasAlreadyShow.set(true)
    }

    override fun hide() {
        if (!mHasAlreadyShow.get()) {
            Log.i(TAG, "当前没有任何窗口了")
            return
        }
//        if (mProvider.getContent() == null) throw IllegalArgumentException("还没初始化contentView")
        mWindowManager.removeView(getCurrentShowContent())
        mHasAlreadyShow.set(false)
    }

    override fun setProvider(provider: IFloatWindowContentProvider) {
        mProvider = provider
    }

    override fun invalidView(provider: IFloatWindowContentProvider) {
    }

    override fun updatePosition(x: Int, y: Int) {
        if (mHasAlreadyShow.get()) {
            val params = getCurrentLayoutParams().apply {
                this.x = x
                this.y = y
            }
            mWindowManager.updateViewLayout(mProvider.getContent(), params)
        } else {
            Log.e(TAG, "当前还没有任何浮窗")
        }
    }

    private fun generateLayoutParams(): WindowManager.LayoutParams {
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            getWindowToken(),
            // Keeps the button presses from going to the background window
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or // Enables the notification to recieve touch events
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or  // Draws over status bar
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.LEFT or Gravity.TOP
        params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        return params
    }

    /**
     * http://liaohuqiu.net/cn/posts/android-windows-manager/
     *
     * @return
     */
    fun getWindowToken(): Int {
        return if (isFloatWindowOpAllowed(mApplicationContext)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else WindowManager.LayoutParams.TYPE_SYSTEM_ERROR
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else if (Build.VERSION.SDK_INT >= 25) {
            //7.1系统修复toast问题，导致悬浮球不能常驻
            WindowManager.LayoutParams.TYPE_PHONE
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams.TYPE_TOAST
        } else {
            WindowManager.LayoutParams.TYPE_SYSTEM_ERROR
        }
    }

    private fun getCurrentLayoutParams(): WindowManager.LayoutParams {
        if (mLayoutParams == null) {
            mLayoutParams = generateLayoutParams()
        }
        return mLayoutParams!!
    }

    private fun getCurrentShowContent(): View {
        if (mCurrentView == null) {
            mCurrentView = mProvider.getContent() ?: LayoutInflater.from(mApplicationContext)
                .inflate(mProvider.getLayoutId(), null, false)
        }
        return mCurrentView!!
    }
}