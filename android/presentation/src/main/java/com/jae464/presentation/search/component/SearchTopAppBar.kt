package com.jae464.presentation.search.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    placeholderText: String,
    onBackClick: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    searchQuery: String
) {
    CenterAlignedTopAppBar(
        title = {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                textStyle = TextStyle(fontSize = 14.sp, lineHeight = 18.sp),
                placeholder = { Text(text = placeholderText, fontSize = 14.sp, lineHeight = 18.sp) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF5F5F5),
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp)
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White),
        modifier = modifier
            .consumeWindowInsets(paddingValues)

    )
}
