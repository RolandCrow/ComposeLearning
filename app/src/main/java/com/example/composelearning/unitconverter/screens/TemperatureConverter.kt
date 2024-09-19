package com.example.composelearning.unitconverter.screens

import android.health.connect.datatypes.units.Temperature
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNodeLifecycleCallback
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composelearning.R
import com.example.composelearning.unitconverter.viewmodels.TemperatureViewModel

@Composable
fun TemperatureConverter(viewModel: TemperatureViewModel) {
    val strCelsius = stringResource(id = R.string.celsius)
    val strFahrenheit = stringResource(id = R.string.fahrenheit)
    val currentValue = viewModel.temperature.collectAsStateWithLifecycle()
    val scale = viewModel.scale.collectAsStateWithLifecycle()
    var result by remember { mutableStateOf("") }
    val calc = {
        val temp = viewModel.convert()
        result = if(temp.isNaN())
            ""
        else
            "$temp${
                if(scale.value == R.string.celsius)
                    strFahrenheit
                else strCelsius
            }"
    }
    val enabled by remember(currentValue.value) {
        mutableStateOf(!viewModel.getTemperatureAsFloat().isNaN())
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {

    }

    @Composable
    fun TemperatureTextField(
        temperature: State<String>,
        modifier: Modifier,
        callback: ()-> Unit,
        viewModel: TemperatureViewModel
    ) {
        TextField(
            value = temperature.value,
            onValueChange = {
                viewModel.setTemperature(it)
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.placeholder)
                )
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
            RadioButton(selected = selected,
                onClick = { onClick(resId) })
            Text(
                text = stringResource(id = resId),
                modifier =Modifier.padding(8.dp)
            )
        }
    }

    @Composable
    fun TemperatureScaleButtonGroup(
        selected: State<Int>,
        modifier: Modifier = Modifier,
        onClick: (Int) -> Unit
    ) {
        val sel = selected.value
        Row(modifier = modifier) {
            TemperatureRadioButton(
                selected = sel == R.string.celsius,
                resId = R.string.fahrenheit ,
                onClick = onClick
            )
        }
        TemperatureRadioButton(
            selected = sel == R.string.fahrenheit,
            resId = R.string.fahrenheit,
            onClick = onClick,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}