package cn.magic.compose_demo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_float_window.*


class MainActivity : AppCompatActivity() {
    private val JUMP_TO_ANOTHER_ACTIVITY_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_float_window)

        val permission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result: Boolean ->
                val msg = if (result) "同意" else "拒绝"
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            }

        request_permission.setOnClickListener {
            //申请访问通讯录权限
            permission.launch(Manifest.permission.READ_CONTACTS)
        }

        val contract = registerJumpToContractCallBack()
        jumpToContract.setOnClickListener {
            contract.launch(null)
        }

        val betweenActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                parserLastActivityData(result)
            }

        //activity之间intent交互
        jumpToActivity.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            betweenActivityResultLauncher.launch(intent)
        }
    }

    private fun parserLastActivityData(result: ActivityResult) {
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                val msg = result.data?.getStringExtra("magic") ?: "没返回intent"
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            }
            else -> {
                Log.i("magic", "返回数据失败")
            }
        }
    }


    private fun registerJumpToContractCallBack(): ActivityResultLauncher<Void> {
        return registerForActivityResult(ActivityResultContracts.PickContact()) { uri: Uri? ->
            if (uri == null) return@registerForActivityResult
            Log.i("magic", "uri=$uri")
            val projection1 = arrayOf(
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER
            )
            val cursor = contentResolver.query(
                uri,
                projection1,
                null,
                null,
                null
            )
            var contactName: String = ""
            var phoneNum: String = ""
            var id = 0L
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    contactName =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    id = cursor.getLong(0)
                    val phoneProjection = arrayOf(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    )
                    //因为一个用户可能有多个电话，所以在数据库上肯定是唯一键值关联电话号码表
                    val phonesCusor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        phoneProjection,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                        null,
                        null
                    )
                    if (phonesCusor != null && phonesCusor.moveToFirst()) {
                        //这里测试我只取数据库中第一行数据
                        phoneNum = phonesCusor.getString(0)
//                            do {
//                                phoneNum = phonesCusor.getString(0)
//                            } while (phonesCusor.moveToNext())
                    }
                    phonesCusor?.close()
                } while (cursor.moveToNext());
            }
            cursor?.close()
            Toast.makeText(this, "$contactName==$phoneNum", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            JUMP_TO_ANOTHER_ACTIVITY_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    //成功
                } else {
                    //失败
                }
            }
        }
    }
}