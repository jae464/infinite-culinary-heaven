package com.jae464.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
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
                Scaffold(
                    modifier = Modifier,
                    content = { padding ->
                        MainNavHost(appState = appState, paddingValues = padding)
                    },
                    bottomBar = {
                        MainBottomBar(
                            modifier = Modifier
                                .navigationBarsPadding()
                                .padding(start = 8.dp, end = 8.dp, bottom = 14.dp),
                            visible = appState.shouldShowBottomBar(),
                            tabs = MainTab.entries.toList(),
                            currentTab = appState.currentTab,
                            onTabSelected = {
                                appState.navigate(it)
                            }

                        )
                    }
                )


            }
        }
    }
}
