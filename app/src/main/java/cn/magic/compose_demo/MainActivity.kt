package cn.magic.compose_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import cn.magic.compose_demo.ui.ComposedemoTheme
import cn.magic.compose_demo.ui.MagicColors
import cn.magic.compose_demo.ui.shapes
import cn.magic.compose_demo.ui.typography

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MagicTheme {
                Scaffold(
                    backgroundColor = Color.White,
                    bodyContent = {
                        listContent()
                    }
                )
            }
        }
    }
}

//主要简单用下flutter这些老朋友写写
@Composable
fun listContent() {
    Column(modifier = Modifier.background(Color.DarkGray)) {
        Text(text = "测试", style = TextStyle(color = Color.Blue, fontSize = TextUnit.Sp(14)))
        Spacer(modifier = Modifier.padding(vertical = 5.dp))
        Row {
            Text(text = "测试1", style = TextStyle(color = Color.Blue, fontSize = TextUnit.Sp(14)))
            Text(text = "测试2", style = TextStyle(color = Color.Blue, fontSize = TextUnit.Sp(14)))
            Text(text = "测试3", style = TextStyle(color = Color.Blue, fontSize = TextUnit.Sp(14)))
        }
    }
}

//Material Design风格
@Composable
fun MagicTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = MagicColors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}