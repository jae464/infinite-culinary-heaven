package com.jae464.presentation.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ContestHistoryScreen(
    padding: PaddingValues
) {
    Column(
        modifier = Modifier.padding(padding)
    ) {
        Text("This is Contest History Screen")
    }
}