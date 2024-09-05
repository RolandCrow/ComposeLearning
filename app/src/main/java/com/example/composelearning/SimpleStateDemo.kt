package com.example.composelearning

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import java.util.Date
import kotlin.random.Random

class SimpleStateDemo: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            RememberWithKeyDemo()
        }
    }

    @Composable
    fun TextFieldDemo1(
        value: String,
        onValueChange: (String) -> Unit
    ) {
        TextField(value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = "Hello")},
            modifier = Modifier.fillMaxWidth()
        )
    }

    @Composable
    @Preview
    fun TextFieldDemo() {
        val state = remember { mutableStateOf("") }
        TextFieldDemo1(value = state.value,
            onValueChange = {state.value = it})
    }
    @Composable
    @Preview
    fun RememberWithKeyDemo() {
        var key by remember { mutableStateOf(false) }
        val date by remember { mutableStateOf(Date()) }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = date.toString())
            Button(onClick = {key = !key}) {
                Text(text = stringResource(id = R.string.click))
            }
        }
    }

    @Composable
    @Preview
    fun SimpleStateDemo1() {
        val num = remember { mutableIntStateOf(Random.nextInt(0,10)) }
        Text(text = num.intValue.toString())
    }

    @Composable
    @Preview
    fun SimpleStateDemo2() {
        val num = remember { mutableIntStateOf(Random.nextInt(0,10)) }
        Text(text = num.toString())
    }

    @Composable
    @Preview
    fun SimpleStatelessComposable1() {
        Text(text = "Hello Compose")
    }

    @Composable
    fun SimpleStatelessComposable2(text: State<String>) {
        Text(text = text.value)
    }


}