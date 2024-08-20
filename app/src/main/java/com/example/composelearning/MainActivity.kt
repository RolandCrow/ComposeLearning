package com.example.composelearning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.composelearning.ui.theme.ComposeLearningTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            Hello()
        }
    }

    @Composable
    @Preview(group = "my-group-1", device = Devices.AUTOMOTIVE_1024p)
    fun GreetingWrapper() {
        Greeting("Jetpack Compose")
    }

    class HelloProvider: PreviewParameterProvider<String> {
        override val values: Sequence<String>
            get() = listOf("PreviewParameterProvider").asSequence()
    }

    @Composable
    fun AltGreeting(name: String = "Jetpack Compose") {
        Text(
            text = stringResource(id = R.string.hello, name),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }

    @Composable
    @Preview
    fun AltGreeting2(
        @PreviewParameter(HelloProvider::class)
        name: String
    ) {
        Text(
            text = stringResource(id = R.string.hello, name),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }

    @Composable
    fun Hello() {
        val name = remember {
           mutableStateOf("")
        }
        val nameEntered = remember {
            mutableStateOf(false)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            if(nameEntered.value) {
                Greeting(name = name.value)
            } else {
                Column(
                    horizontalAlignment = 
                        Alignment.CenterHorizontally
                ) {
                    Welcome()
                    TextAndButton(name = name, nameEntered = nameEntered)
                }
            }
        }
    }
    
    @Composable
    fun Welcome() {
        Text(
            text = stringResource(id = R.string.welcome),
            style = MaterialTheme.typography.bodyLarge
        )
    }

    @Composable
    fun Greeting(name: String) {
        Text(
            text = stringResource(id = R.string.hello, name),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
    
    @Composable
    fun TextAndButton(name: MutableState<String>,
                      nameEntered: MutableState<Boolean>) {
        Row(modifier = Modifier.padding(top = 8.dp)) {
            TextField(
                value = name.value, 
                onValueChange = {
                name.value = it
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.hint))
                },
                modifier = Modifier
                    .alignByBaseline()
                    .weight(1.0F),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    capitalization = KeyboardCapitalization.Words,
                ),
                keyboardActions = KeyboardActions(onAny = {
                    nameEntered.value = true
                })
                
            )
            Button(modifier = Modifier
                .alignByBaseline()
                .padding(8.dp)
                , onClick = {
                    nameEntered.value = true
                }) {
                Text(text = stringResource(id = R.string.done))
            }
        }
    }
}
