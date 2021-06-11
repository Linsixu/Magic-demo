package cn.magic.compose_demo.recycler

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import cn.magic.compose_demo.R
import cn.magic.compose_demo.recycler.bean.TestBean
import cn.magic.compose_demo.recycler.item.DynamicHeadHelper
import cn.magic.compose_demo.recycler.item.content.ContentAdapter
import cn.magic.compose_demo.recycler.item.head.HeadAdapter
import kotlinx.android.synthetic.main.activity_new_recycler.*

/**
 * User: linsixu@yy.com
 * Date: 2021/6/8
 * Desc: 描述
 * SinceVer: TODO:哪个版本添加的
 */
class NewRecyclerViewActivity : AppCompatActivity() {
    private val mSource = ArrayList<TestBean>()
    private lateinit var mHeadAdapter: HeadAdapter
    private lateinit var mContentAdapter: ContentAdapter<TestBean>
    private var mTime = 0

    private val mHelper by lazy { DynamicHeadHelper() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_recycler)
        buildTestData()
        val adapter = initAdapter()
//        adapter.addAdapter(0,mHeadAdapter) (可以动态插入列表所在位置去展示)
        new_recycler.adapter = adapter
        new_recycler.layoutManager = LinearLayoutManager(this)
        mHelper.dynamicCalculate(new_recycler, headAdapter = mHeadAdapter, contact = adapter) {
            initBuildAdapter()
            Handler().postDelayed({
                loadMore()
            }, 3000)
        }
    }

    private fun initAdapter(): ConcatAdapter {
        mHeadAdapter = HeadAdapter()
        mContentAdapter = ContentAdapter(mSource)
        return ConcatAdapter(mContentAdapter)
    }

    private fun buildTestData() {
        for (i in 0..100) {
            mSource.add(TestBean("magic in $i position,action time $mTime"))
        }
    }

    private fun initBuildAdapter() {
        mSource.clear()
        buildTestData()
        mContentAdapter.notifyDataSetChanged()
        mTime++
    }

    private fun loadMore() {
        for (i in 101..151) {
            mSource.add(TestBean("new magic in $i position"))
        }
        mContentAdapter.notifyDataSetChanged()
    }
}