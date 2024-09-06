package com.example.composelearning

import android.os.Bundle
import android.os.PersistableBundle
import androidx.compose.runtime.livedata.observeAsState
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MyViewModel: ViewModel() {
    private val _text: MutableLiveData<String> =
        MutableLiveData<String>("Hello #3")

    val text: LiveData<String>
        get() = _text

    fun setText(value: String) {
        _text.value = value
    }
}

    class ViewModelDemoActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                ViewModelDemo1()
            }
        }


        @Composable
        @Preview
        fun ViewModelDemo1() {
            val viewModel: MyViewModel = viewModel()
            val state1 = remember { mutableStateOf("Hello #1") }
            val state2 = remember { mutableStateOf("Hello #2") }
            val state3 = viewModel.text.observeAsState()
            Column(modifier = Modifier.fillMaxWidth()) {
                MyTextField(value = state1.value) {state1.value = it}
                MyTextField(value = state2.value) {state2.value = it}
                MyTextField(value = state3.value) {
                    viewModel.setText(it)
                }
            }

        }

        @Composable
        fun MyTextField(
            value: String?,
            onValueChange: (String) -> Unit
        ) {
            value?.let {
                TextField(
                    value = it,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
