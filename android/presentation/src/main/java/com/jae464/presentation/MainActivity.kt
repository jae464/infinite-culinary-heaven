package com.jae464.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jae464.presentation.main.MainBottomBar
import com.jae464.presentation.main.MainNavHost
import com.jae464.presentation.main.MainTab
import com.jae464.presentation.ui.theme.CulinaryHeavenTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


            CulinaryHeavenTheme {

                val appState = rememberAppState()
                val snackBarHostState = remember { SnackbarHostState() }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = { padding ->
                        MainNavHost(
                            appState = appState,
                            paddingValues = padding,
                            onShowSnackBar = { message, action ->
                                snackBarHostState.showSnackbar(
                                    message = message,
                                    actionLabel = action,
                                )
                            }
                        )
                    },
                    bottomBar = {
                        MainBottomBar(
                            modifier = Modifier
                                .navigationBarsPadding()
                                .padding(bottom = 14.dp),
                            visible = appState.shouldShowBottomBar(),
                            tabs = MainTab.entries.toList(),
                            currentTab = appState.currentTab,
                            onTabSelected = {
                                appState.navigate(it)
                            }
                        )
                    },
                    snackbarHost = { SnackbarHost(snackBarHostState) }
                )
            }
        }
    }
}
