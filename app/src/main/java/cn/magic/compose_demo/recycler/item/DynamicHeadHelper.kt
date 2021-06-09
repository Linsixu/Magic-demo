package cn.magic.compose_demo.recycler.item

import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * User: linsixu@yy.com
 * Date: 2021/6/9
 * Desc: 动态添加头部
 * SinceVer: TODO:哪个版本添加的
 */
class DynamicHeadHelper {
    private val TAG = "magic"
    fun dynamicCalculate(
        recyclerView: RecyclerView,
        headAdapter: RecyclerView.Adapter<*>,
        contact: ConcatAdapter
    ) {
        var lastY = 0f
        recyclerView.setOnTouchListener { v: View, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastY = event.y
                    recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE &&
                            !recyclerView.canScrollVertically(-1)
                }
                MotionEvent.ACTION_MOVE -> {
                    if (recyclerView.scrollState != RecyclerView.SCROLL_STATE_SETTLING &&
                        !recyclerView.canScrollVertically(-1)
                    ) {
                        if (event.y < lastY) {
                            //手指往上滑动，给recyclerView处理滑动事件
                            lastY = event.y
                            false
                        } else {
                            lastY = event.y
                            //往下拉，把头部View展示出来
                            if (!contact.adapters.contains(headAdapter)) {
                                contact.addAdapter(0, headAdapter)
                            }
                            true
                        }
                    } else {
                        false
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (contact.adapters.contains(headAdapter)) {
                        contact.removeAdapter(headAdapter)
                        true
                    } else {
                        false
                    }
                }
                else -> {
                    false
                }
            }
        }
    }
}