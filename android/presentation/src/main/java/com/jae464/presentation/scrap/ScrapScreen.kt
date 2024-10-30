package com.jae464.presentation.scrap

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ScrapRoute(
    padding: PaddingValues
) {
    ScrapScreen(
        padding = padding
    )
}
@Composable
fun ScrapScreen(
    padding: PaddingValues
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        Text("This is Scrap Screen")
    }
}