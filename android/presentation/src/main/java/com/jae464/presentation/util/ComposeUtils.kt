package com.jae464.presentation.util

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Composable
fun LaunchedEffectWithLifecycle(
    key1: Any? = LocalLifecycleOwner.current,
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1) {
        lifecycleOwner.lifecycleScope.launch(
            coroutineContext + context
        ) {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }
}

@Composable
fun rememberGalleryLauncher(
    onImageSelected: (Uri?) -> Unit
): ManagedActivityResultLauncher<String, Uri?> {
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            onImageSelected(uri)
        }
    )
    return galleryLauncher
}

@Composable
fun rememberPermissionLauncher(
    onGranted: () -> Unit = {},
    onDenied: () -> Unit = {}
): ManagedActivityResultLauncher<String, Boolean> {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onGranted()
        } else {
            onDenied()
        }
    }
    return permissionLauncher
}
