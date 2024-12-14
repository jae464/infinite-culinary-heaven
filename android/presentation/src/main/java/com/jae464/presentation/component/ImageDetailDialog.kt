package com.jae464.presentation.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailDialog(
    imageUrl: String,
    onDismiss: () -> Unit
) {

    val zoomState = rememberZoomState()

    BackHandler(enabled = true) {
        onDismiss()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        HeavenTopAppBar(
            title = "",
            navigationIcon = Icons.Default.ArrowBack,
            useNavigationIcon = true,
            onNavigationClick = onDismiss
        )
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .zoomable(zoomState),
            onSuccess = {
                zoomState.setContentSize(it.painter.intrinsicSize)
            },
            contentScale = ContentScale.Fit
        )
    }
}