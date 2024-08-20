package com.example.composelearning

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class Factorial : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            Factorials()
        }
    }

    @Composable
    fun Factorials() {
        var expanded by remember { mutableStateOf(false) }
        var text by remember {
            mutableStateOf(
                factorialAsString(0)
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.clickable {
                    expanded = true
                },
                text = text,
                style = MaterialTheme.typography.headlineMedium
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {expanded = false},
            ) {
                for(n in 0 until 10) {
                    DropdownMenuItem(text = {
                        Text(text = "$n!")
                    },
                        onClick = {
                            expanded = false
                            text = factorialAsString(n)
                        })
                }
            }
        }
    }

    @Composable
    @Preview
    fun ButtonDemo(enabled: Boolean = true) {
        Box {
            Button(
               onClick = {
                   println("clicked")
               },
               enabled = enabled 
            ) {
                Text(text = "Click me!")
            }
        }
    }

    @Composable
    @Preview
    fun BoxDemo() {
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(width = 100.dp, height = 100.dp)
                    .background(Color.Green)
            )
            Box(
                modifier = Modifier
                    .size(width = 80.dp, height = 80.dp)
                    .background(Color.Yellow)
            )
            Text(
                text = "Hello",
                color = Color.Black,
                modifier = Modifier.align(Alignment.TopStart)
            )
        }
    }



    private fun factorialAsString(n: Int): String {
        var result = 1L
        for(i in 1..n) {
            result *= i
        }
        return "$n! = $result"
    }


}