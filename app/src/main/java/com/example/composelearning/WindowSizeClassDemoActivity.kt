package com.example.composelearning

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowLayoutInfo
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.WindowInfoTracker
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
val LocalWindowSizeClass = compositionLocalOf { WindowSizeClass.calculateFromSize(DpSize.Zero) }

class WindowSizeClassDemoActivity  : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val activity = this@WindowSizeClassDemoActivity
                setContent {
                    MaterialTheme(
                        colorScheme = defaultColorScheme()
                    ) {
                        val windowLayoutInfo by WindowInfoTracker.getOrCreate(
                            context = activity
                        ).windowLayoutInfo(activity = activity).collectAsState(
                            initial = null
                        )
                        CompositionLocalProvider(
                            LocalWindowSizeClass provides calculateWindowSizeClass(
                                activity = activity)
                        ) {
                            WindowSizeClassDemoScreen(
                                windowLayoutInfo = windowLayoutInfo
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WindowSizeClassDemoScreen(
    windowLayoutInfo: WindowLayoutInfo? = null
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }, scrollBehavior = scrollBehavior
            )
        }
    ) {
        val list = (1..42).toList()
        AdaptiveScreen(
            paddingValues = it,
            list = list,
            windowLayoutInfo = windowLayoutInfo
            )
    }
}

@Composable
fun SimpleScreen(
    paddingValues: PaddingValues, list: List<Int>
) {
    NumberList(paddingValues = paddingValues, list = list)
}

@Composable
fun NumberList(
    paddingValues: PaddingValues, list: List<Int>
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues = paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = list
        ) {
            NumberItem(number = it)
        }
    }
}

@Composable
private fun NumberItem(number: Int) {
    Text(
       modifier = Modifier
           .fillMaxWidth()
           .padding(vertical = 8.dp, horizontal = 16.dp)
           .border(width = 1.dp, color = MaterialTheme.colorScheme.primary),
        text = number.toString(),
        style = MaterialTheme.typography.displayLarge,
        textAlign = TextAlign.Center
    )
}

@Composable
fun defaultColorScheme() = with(isSystemInDarkTheme()) {
    val hasDynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val context = LocalContext.current
    when(this) {
        true -> if(hasDynamicColor) {
            dynamicDarkColorScheme(context)
        } else {
            darkColorScheme()
        }
        false -> if(hasDynamicColor) {
            dynamicLightColorScheme(context)
        } else {
            lightColorScheme()
        }
    }
}

@Composable
fun NumbersGrid(
    paddingValues: PaddingValues, list: List<Int>, column: Int = 2
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(column),
        modifier = Modifier.padding(paddingValues = paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = list
        ) {
            NumberItem(number = it)
        }
    }
}

@Composable
fun AdaptiveScreen(
    paddingValues: PaddingValues,
    list: List<Int>,
    windowLayoutInfo: WindowLayoutInfo? = null
) {
    var numColumnWhenExpanded = 3
    windowLayoutInfo?.displayFeatures?.forEach { displayFeature ->
        (displayFeature as FoldingFeature).run {
            if(orientation == FoldingFeature.Orientation.VERTICAL) {
                numColumnWhenExpanded = 2
            }
        }
    }

    with(LocalWindowSizeClass.current) {
        if (widthSizeClass == WindowWidthSizeClass.Compact) {
            NumberList(paddingValues = paddingValues, list = list)
        } else {
            NumbersGrid(paddingValues = paddingValues, list = list, column = when(widthSizeClass){
                WindowWidthSizeClass.Medium -> 2
                else -> numColumnWhenExpanded
            })
        }
    }
}