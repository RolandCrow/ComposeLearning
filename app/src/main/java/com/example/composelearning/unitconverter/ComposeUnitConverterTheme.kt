package com.example.composelearning.unitconverter

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composelearning.R

    private val androidGreen = Color(0xFF3DDC84)
    private val androidGreenDark = Color(0xFF20B261)
    private val orange = Color(0xFFFFA500)
    private val orangeDark = Color(0xFFCC8400)

    private val LightColorPalette = lightColorScheme(
        primaryContainer = androidGreen,
        secondary = orange
    )

    private val DarkColorPalette = darkColorScheme(
        primaryContainer = androidGreenDark,
        secondary = orangeDark
    )

    @Composable
    fun ComposeUnitConverterTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit
    ) {
        val colorScheme = if(darkTheme) {
            DarkColorPalette
        } else {
            LightColorPalette.copy(secondary = colorResource(id =
            R.color.orange_dark
            ))
        }
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
    
    @Composable
    @Preview
    fun CutCornerShapeDemo() {
        MaterialTheme(
            shapes = Shapes(small = CutCornerShape(8.dp))
        ) {
            Button(onClick = {},
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Click me")
            }
        }
    }

    @Composable
    @Preview
    fun MaterialThemeDemo() {
        MaterialTheme(
            typography = Typography(
                headlineLarge = TextStyle(color = Color.Red)
            )
        ) {
            Row {
                Text(
                    text = "Hello",
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.width(2.dp))
                MaterialTheme(
                    typography = Typography(
                        headlineLarge = TextStyle(color = Color.Blue)
                    )
                ) {
                    Text(text = "Compose",
                        style = MaterialTheme.typography.headlineLarge                       )
                }
            }
        }
    }
