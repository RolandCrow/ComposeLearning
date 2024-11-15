package com.example.composelearning

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class AnimationDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            Content()
        }
    }
}

@Composable
fun Content() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val composable = listOf<@Composable ()  -> Unit>(
            { StateChangeDemo()},
            { SingleValueAnimationDemo()},
            { MultipleValuesAnimationDemo()},
            { AnimatedVisibilityDemo()},
            { SizeChangeAnimationDemo()},
            { CrossfadeAnimationDemo()},
            { InfiniteRepeatableDemo()}
        )
        composable.forEachIndexed { index, function ->
            animationDemoItem(index % 2 == 1) {function()}
        }
    }
}

fun LazyListScope.animationDemoItem(
    alternativeBackground: Boolean,
    content: @Composable LazyItemScope.() -> Unit
) {
    item {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (alternativeBackground)
                        MaterialTheme.colorScheme.tertiaryContainer
                    else
                        MaterialTheme.colorScheme.background
                ),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Preview
@Composable
fun CrossfadeAnimationDemo() {
    var isFirstScreen by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(192.dp)
    ) {
        Switch(
            checked = isFirstScreen,
            onCheckedChange = {
                isFirstScreen = !isFirstScreen
            },
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )
        Crossfade(targetState = isFirstScreen, label = "") {
            if(it) {
                Screen(
                    text = stringResource(R.string.letter_w),
                    backgroundColor = Color.Gray
                )
            } else {
                Screen(
                    text = stringResource(id = R.string.letter_i),
                    backgroundColor = Color.LightGray
                )
            }
        }
    }
}

@Preview
@Composable
fun StateChangeDemo() {
    var toggled by remember {
        mutableStateOf(false)
    }
    val color = if(toggled)
        Color.White
    else
        Color.Red
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            toggled = !toggled
        }) {
            Text(
                stringResource(id = R.string.toggle)
            )
        }
        Box(
            modifier = Modifier
                .padding(top = 32.dp)
                .background(color = color)
                .size(128.dp)
        )
    }
}

@Composable
@Preview
fun SingleValueAnimationDemo() {
    var toggled by remember {
        mutableStateOf(false)
    }
    val color by animateColorAsState(
        targetValue = if(toggled)
            Color.White
        else
            Color.Red,
        animationSpec = spring(stiffness = Spring.StiffnessVeryLow), label = ""
    )
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            toggled = !toggled
        }) {
            Text(
                stringResource(id = R.string.toggle)
            )
        }
        Box(
            modifier = Modifier
                .padding(top = 32.dp)
                .background(color = color)
                .size(128.dp)
        )
    }
}

@Composable
@Preview
fun MultipleValuesAnimationDemo() {
    var toggled by remember {
        mutableStateOf(false)
    }
    val transition = updateTransition(
        targetState = toggled,
        label = "toggledTransition"
    )
    val borderWidth by transition.animateDp (label = "borderWidth") { state ->
        if(state)
            10.dp
        else
            1.dp
    }
    val degrees by transition.animateFloat(label = "degreesTransition") { state ->
        if(state) -90F
        else
            0F
    }
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            toggled = !toggled
        }) {
            Text(
                stringResource(id = R.string.toggle)
            )
        }
        Box(
            modifier = Modifier
                .padding(top = 32.dp)
                .border(
                    width = borderWidth,
                    color = Color.Black
                )
                .size(28.dp)
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                modifier = Modifier.rotate(degrees = degrees)
            )
        }
    }
}

@Composable
@Preview
fun AnimatedVisibilityDemo() {
    var visible by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            visible = !visible
        }) {
            Text(
                stringResource(
                    id = if(visible)
                        R.string.hide
                    else
                        R.string.show
                )
            )
        }
        AnimatedVisibility(
            visible = visible,
            enter = slideInHorizontally(),
            exit = slideOutVertically()
        ) {
            Box(
                modifier = Modifier
                    .padding(32.dp)
                    .background(color = Color.Red)
                    .size(128.dp)
            )
        }
    }
}

@Preview
@Composable
fun SizeChangeAnimationDemo() {
    var size by remember { mutableFloatStateOf(1F) }
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Slider(
            value = size,
            valueRange = (1F..4F),
            steps = 3,
            onValueChange = {
                size = it
            },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = stringResource(id = R.string.lines),
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .animateContentSize(),
            maxLines = size.toInt(),
            color = Color.Blue
        )
    }
}

@Composable
fun Screen(
    text: String,
    backgroundColor: Color = Color.White
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
@Preview
fun InfiniteRepeatableDemo() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val degrees by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 359F,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1500
                0F at 0
                359F at 1500
            },
            repeatMode = RepeatMode.Restart
        ), label = ""
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(192.dp),
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier.rotate(degrees = degrees)
        )
    }
}