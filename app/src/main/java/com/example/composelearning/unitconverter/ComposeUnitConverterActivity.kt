package com.example.composelearning.unitconverter

import android.os.Bundle
import android.os.PersistableBundle
import androidx.compose.material3.NavigationBarItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composelearning.R
import com.example.composelearning.unitconverter.screens.ComposeUnitConverterScreen
import com.example.composelearning.unitconverter.screens.DistancesConverter
import com.example.composelearning.unitconverter.screens.TemperatureConverter
import com.example.composelearning.unitconverter.viewmodels.ViewModelFactory
import kotlinx.coroutines.launch

class ComposeUnitConverterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val factory = ViewModelFactory(Repository(applicationContext))
        setContent { 
            ComposeUnitConverter(factory = factory)
        }
    }

    @Composable
    fun ComposeUnitConverter(factory: ViewModelFactory) {
        val navController= rememberNavController()
        val menuItems = listOf("Item 2", "Item 1")
        val snackbarCoroutineScope = rememberCoroutineScope()
        val snackbarHostState = remember {SnackbarHostState()}
        ComposeUnitConverterTheme {
            Scaffold(
                snackbarHost = {SnackbarHost(snackbarHostState)},
                topBar = {
                    ComposeUnitConverterTopBar(menuItems = menuItems) { s->
                        snackbarCoroutineScope.launch { 
                            snackbarHostState
                        }
                    }
                },
                bottomBar = {
                    ComposeUnitConverterBottomBar(navController = navController)
                }
            ) {
                ComposeUnitConverterNavHost(
                    navController = navController, 
                    factory = factory, 
                    modifier = Modifier.padding(it)
                )
            }
        }
    }

    @OptIn( ExperimentalMaterial3Api::class)
    @Composable
    fun ComposeUnitConverterTopBar(menuItems: List<String>, onClick: (String) -> Unit) {
        var menuOpened by remember { mutableStateOf(false) }
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.app_name))
        },
            actions = {
                Box{
                    IconButton(
                        onClick = {
                            menuOpened = true
                        }
                    ) {
                        Icon(Icons.Default.MoreVert, "")
                    }
                    DropdownMenu(
                        expanded = menuOpened,
                        onDismissRequest = {
                            menuOpened = false
                        }) {
                        menuItems.forEachIndexed {index,s ->
                            if(index > 0) Divider()
                            DropdownMenuItem(
                                text = { Text(s) },
                                onClick = {
                                    menuOpened = false
                                    onClick(s)
                                })
                        }
                    }
                }
            }

        )
    }


    @Composable
    fun ComposeUnitConverterBottomBar(
        navController: NavHostController
    ) {
        BottomAppBar {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            ComposeUnitConverterScreen.screens.forEach { screen ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            launchSingleTop
                        }
                    },
                    label = {
                        Text(text = stringResource(id = screen.label))
                    },
                    icon = {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = stringResource(id = screen.label)
                        )
                    },
                    alwaysShowLabel = true
                )
            }
        }
    }


    @Composable
    fun ComposeUnitConverterNavHost(
        navController: NavHostController,
        factory: ViewModelProvider.Factory?,
        modifier: Modifier
    ) {
        NavHost(
            navController = navController,
            startDestination = ComposeUnitConverterScreen.route_temperature,
            modifier = modifier
        ) {
            composable(ComposeUnitConverterScreen.route_temperature) {
                TemperatureConverter(viewModel = viewModel(factory = factory))
            }
            composable(ComposeUnitConverterScreen.route_distance) {
                DistancesConverter(viewModel = viewModel(factory = factory))
            }
        }
    }
}