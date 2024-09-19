package com.example.composelearning.unitconverter.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.composelearning.R

sealed class ComposeUnitConverterScreen(
    val route: String,
    @StringRes val label: Int,
    val icon: ImageVector
) {
    companion object {
        val screens = listOf(
            Temperature,
            Distance
        )

        const val route_temperature="temperature"
        const val route_distance = "distances"
    }

    private data object Temperature: ComposeUnitConverterScreen(
        route_temperature,
        R.string.temperature,
        Icons.Default.PlayArrow
    )

    private data object Distance:ComposeUnitConverterScreen(
        route_distance,
        R.string.distances,
        Icons.Default.Star
    )
}