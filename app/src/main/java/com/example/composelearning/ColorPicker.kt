package com.example.composelearning

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min

class ColorPicker : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            BoxWithConstraints(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            ) {
                Column(
                    modifier = Modifier.width(min(400.dp, maxWidth)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var color by remember { mutableStateOf(Color.Magenta) }
                    ColorPickers(color = color, colorChanged = {color = it}
                    )
                    Text(
                       modifier = Modifier
                           .fillMaxWidth()
                           .background(color),
                        text = color.toArgb().toUInt().toString(16),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.displayMedium.merge(
                            color = color.complementary()
                        )
                    )
                }
            }
        }
    }

    @Composable
    fun ColorPickers(color: Color, colorChanged: (Color) -> Unit) {
        val red = color.red
        val green = color.green
        val blue = color.blue
        Column {
            Slider(
                value = red,
                onValueChange = {
                    colorChanged(Color(it,green,blue))
                }
            )
            Slider(
                value = green,
                onValueChange = {
                    colorChanged(Color(red,it,blue))
                }
            )
            Slider(
                value = blue,
                onValueChange = {
                    colorChanged(Color(red,green,it))
                }
            )
        }
    }

    private fun Color.complementary() = Color(
        red = 1f - red,
        green = 1f - green,
        blue = 1f - blue
    )
}