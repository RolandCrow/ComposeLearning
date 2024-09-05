package com.example.composelearning

import android.health.connect.datatypes.units.Temperature
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class FlowOfEvents : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            FlowOfEventsDemo()
        }
    }
    @Composable
    @Preview
    fun FlowOfEventsDemo() {
        val strCelsius = stringResource(id = R.string.celsius)
        val strFahrenheit = stringResource(id = R.string.fahrenheit)
        var temperature by remember { mutableStateOf("") }
        var scale by remember { mutableIntStateOf(R.string.celsius) }
        var convertedTemperature by remember { mutableFloatStateOf(Float.NaN) }
        val calc = {
            val temp = temperature.toFloat()
            convertedTemperature = if (scale == R.string.celsius)
                (temp * 1.8F) + 32F
            else
                (temp - 32F) / 1.8F
        }
        val result = remember(convertedTemperature) {
            if(convertedTemperature.isNaN())
                ""
            else
            "${convertedTemperature}${
                if(scale == R.string.celsius)
                strFahrenheit
                else strCelsius
            }"
        }
        val enabled = temperature.isNotBlank()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TemperatureTextField(
                temperature = temperature,
                onValueChange = {temperature = it},
                modifier = Modifier.padding(bottom = 16.dp),
                callback = calc
            )
            TemperatureScaleButtonGroup(
                selected = scale,
                radioButtonClicked = {scale = it},
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(
                onClick = calc,
                enabled = enabled
            ) {
                Text(text = stringResource(id = R.string.convert))
            }
            if(result.isNotEmpty()) {
                Text(
                    text = result,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }

    @Composable
    fun TemperatureTextField(
       temperature: String,
       onValueChange: (String) -> Unit,
       modifier: Modifier = Modifier,
       callback: () -> Unit
    ) {
        TextField(
            value = temperature,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = stringResource(id =
                R.string.placeholder))
            },
            modifier = modifier,
            keyboardActions = KeyboardActions(onAny = {
                callback()
            }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            singleLine = true
        )
    }

    @Composable
    fun TemperatureRadioButton(
        selected: Boolean,
        resId: Int,
        onClick: (Int) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            RadioButton(
                selected = selected,
                onClick = {
                    onClick(resId)
                }
            )
            Text(
                text = stringResource(id = resId),
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }
    }

    @Composable
    fun TemperatureScaleButtonGroup(
        selected: Int,
        radioButtonClicked: (Int) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Row(modifier = modifier) {
            TemperatureRadioButton(
                selected = selected == R.string.celsius,
                resId = R.string.celsius,
                onClick = {radioButtonClicked(R.string.celsius)}
            )
            TemperatureRadioButton(
                selected = selected == R.string.fahrenheit,
                resId = R.string.fahrenheit,
                onClick = {radioButtonClicked(R.string.fahrenheit)},
                modifier = Modifier.padding(start = 16.dp)
            )
        }

    }
}