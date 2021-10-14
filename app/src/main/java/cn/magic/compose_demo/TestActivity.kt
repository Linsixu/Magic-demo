package cn.magic.compose_demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_target_test.*
import org.json.JSONObject

/**
 * User: linsixu@yy.com
 * Date: 2021/4/19
 * Desc: 描述
 * SinceVer: TODO:哪个版本添加的
 */
class TestActivity : AppCompatActivity() {
    val a = "{\n" +
            "\t\"type\": 107,\n" +
            "\t\"data\": {\n" +
            "\t\t\"service_type\": \"10013\",\n" +
            "\t\t\"service_info\": {\n" +
            "\t\t\t\"content\": {\n" +
            "\t\t\t\t\"content_type\": \"fans_group_join\"\n" +
            "\t\t\t},\n" +
            "\t\t\t\"mark_info\": [{\n" +
            "\t\t\t\t\"ext\": {\n" +
            "\t\t\t\t\t\"height\": 57,\n" +
            "\t\t\t\t\t\"width\": 105\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"invalid_time\": -1,\n" +
            "\t\t\t\t\"mark_pic\": \"https:\\/\\/ala-gift.cdn.bcebos.com\\/gift\\/2021-9\\/1632637239819\\/1m-3%E5%A4%87%E4%BB%BD%202.png\",\n" +
            "\t\t\t\t\"type\": 5\n" +
            "\t\t\t}, {\n" +
            "\t\t\t\t\"ext\": {\n" +
            "\t\t\t\t\t\"background_color\": \"#FFBB33\",\n" +
            "\t\t\t\t\t\"fans_group_name\": \"Sup\",\n" +
            "\t\t\t\t\t\"height\": 48,\n" +
            "\t\t\t\t\t\"width\": 60\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"invalid_time\": -1,\n" +
            "\t\t\t\t\"mark_pic\": \"https:\\/\\/ala-gift.cdn.bcebos.com\\/gift\\/2021-7\\/1627473460111\\/1%403x.png\",\n" +
            "\t\t\t\t\"type\": 1\n" +
            "\t\t\t}],\n" +
            "\t\t\t\"msg_type\": \"13\",\n" +
            "\t\t\t\"portrait\": \"https:\\/\\/user-center.cdn.bcebos.com\\/head\\/raw\\/uc.101.304b9205.DQ-ritpt2BTSkvIqWZrDQA?x-bce-process=image\\/resize,m_lfit,w_200,h_200\\u0026autime=33173\",\n" +
            "\t\t\t\"room_id\": 4.877090383e+09,\n" +
            "\t\t\t\"user_id\": 1.454340002e+09,\n" +
            "\t\t\t\"user_name\": \"Magic旭\"\n" +
            "\t\t}\n" +
            "\t}\n" +
            "}"

    val test321 = "{\"room_id\":4.877090383e+09,\"user_id\":1.454340002e+09}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target_test)
        val a = JSONObject(a)
        val data = JSONObject(a.optString("data"))
        val serviceInfo = data.optJSONObject("service_info")
        Log.i("magic", "serviceInfo=$serviceInfo")

        val json = JSONObject(test321)

        click_finish.setOnClickListener {
            val intent = Intent()
            intent.putExtra("magic", "Hello world")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }



        test.setOnClickListener {
//            val a = BigDecimal("4.867293051e+09")
//            val b = BigDecimal("4874744605")
//            Toast.makeText(
//                this,
//                "a=${a.toPlainString()},b=${b.toPlainString()}",
//                Toast.LENGTH_SHORT
//            ).show()
            val output = serviceInfo.optString("room_id")
            val output1 = serviceInfo.getString("room_id")
            val a = json.toString()
            Log.i("magic", "a=$a")
            val room_id_test = json.optString("room_id")
            val room_id_test3 = json.optString("user_id")
            Log.i("magic", "room_id=$output,output1=$output1")
            Log.i("magic", "room_id_test=$room_id_test,room_id_test3=$room_id_test3")
        }
    }
}