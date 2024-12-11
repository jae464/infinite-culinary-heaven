package com.jae464.presentation.component

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainTabBackHandler() {
    var backPressedCount by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    BackHandler {
        if (backPressedCount > 0) {
            (context as? Activity)?.finish()
        } else {
            backPressedCount++
            Toast.makeText(context, "한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()

            scope.launch {
                delay(2000)
                backPressedCount = 0
            }
        }
    }
}