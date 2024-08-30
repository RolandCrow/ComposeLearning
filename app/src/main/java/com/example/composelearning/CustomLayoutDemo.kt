package com.example.composelearning

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random

class CustomLayoutDemo: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            CustomLayoutDemoPreview()
        }
    }

    private fun  simpleFlexBoxMeasurePolicy(): MeasurePolicy =
        MeasurePolicy{ measurables, constraints ->
            val placeable = measurables.map { measurables ->
                measurables.measure(constraints)
            }
            layout(
                constraints.maxWidth,
                constraints.maxHeight
            ) {
                var yPos = 0
                var xPos = 0
                var maxY = 0
                placeable.forEach {placeable ->
                    if(xPos + placeable.width > constraints.maxWidth) {
                        xPos = 0
                        yPos += maxY
                        maxY = 0
                    }
                    placeable.placeRelative(
                        x = xPos,
                        y = yPos
                    )
                    xPos += placeable.width
                    if (maxY < placeable.height) {
                        maxY = placeable.height
                    }

                }
            }
        }
    @Composable
    fun SimpleFlexBox(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    ) {
        Layout(
            modifier = modifier,
            content = content,
            measurePolicy = simpleFlexBoxMeasurePolicy()
        )
    }

    @Composable
    @Preview
    fun CustomLayoutDemoPreview() {
        SimpleFlexBox {
            for(i in 0..42) {
                ColoredBox()
            }
        }
    }

    private fun randomIn123() = Random.nextInt(1,4)
    private fun randomColor() = when (randomIn123()) {
        1 -> Color.Red
        2 -> Color.Green
        else -> Color.Blue
    }

    @Composable
    fun ColoredBox() {
        Box(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = Color.Black
                )
                .background(randomColor())
                .width((40 * randomIn123()).dp)
                .height((10 * randomIn123()).dp)
        )
    }


}