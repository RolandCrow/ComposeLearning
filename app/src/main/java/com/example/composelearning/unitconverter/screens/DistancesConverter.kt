package com.example.composelearning.unitconverter.screens

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
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composelearning.R
import com.example.composelearning.unitconverter.viewmodels.DistanceViewModel

@Composable
fun DistancesConverter(viewModel: DistanceViewModel) {
    val strMeter = stringResource(id = R.string.meter)
    val strMile = stringResource(id = R.string.mile)
    val currentValue = viewModel.distance.collectAsStateWithLifecycle()
    val unit = viewModel.unit.collectAsStateWithLifecycle()
    val convertedValue by viewModel.convertedDistance.collectAsStateWithLifecycle()
    val result by remember(convertedValue) {
        mutableStateOf(
            if(convertedValue.isNaN())
                ""
            else
            "$convertedValue ${
                if(unit.value == R.string.meter)
                    strMile
                else strMeter
            }"
        )
    }
    val calc = {
        viewModel.convert()
    }

    val enabled by remember(currentValue.value) {
        mutableStateOf(!viewModel.getDistanceAsFloat().isNaN())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {

    }

    @Composable
    fun DistanceTextField(
        distance: State<String>,
        modifier: Modifier = Modifier,
        callback: () -> Unit,
        viewModel: DistanceViewModel
        ) {
        TextField(
            value = distance.value,
            onValueChange = {
                viewModel.setDistance(it)
            },
            placeholder = {
                Text(text = stringResource(id = R.string.placeholder_distance))
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
    fun DistanceRadioButton(
        selected: Boolean,
        resId: Int,
        onClick: (Int) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
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
                    .padding(8.dp)
            )
        }
    }

    @Composable
    fun DistanceScaleButtonGroup(
        selected: State<Int>,
        modifier: Modifier = Modifier,
        onClick: (Int) -> Unit
    ) {
        val sel = selected.value
        Row(modifier = modifier) {
            DistanceRadioButton(
                selected = sel == R.string.meter,
                resId = R.string.meter,
                onClick = onClick
            )
            DistanceRadioButton(
                selected = sel == R.string.mile,
                resId = R.string.mile,
                onClick = onClick,
                modifier = Modifier.padding(16.dp)
            )
        }

    }
}