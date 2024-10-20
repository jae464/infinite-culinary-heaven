package com.jae464.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
internal fun MainBottomBar(
    modifier: Modifier = Modifier,
    visible: Boolean,
    tabs: List<MainTab>,
    currentTab: MainTab?,
    onTabSelected: (MainTab) -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideIn { IntOffset(0, it.height) },
        exit = fadeOut() + slideOut { IntOffset(0, it.height) }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart=28.dp, topEnd=28.dp),
                ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            tabs.forEach { tab ->
                MainBottomBarItem(
                    tab = tab,
                    selected = tab == currentTab,
                    onClick = { onTabSelected(tab) },
                )
            }
        }
    }
}

@Composable
private fun RowScope.MainBottomBarItem(
    modifier: Modifier = Modifier,
    tab: MainTab,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .weight(1f)
            .fillMaxHeight()
            .selectable(
                selected = selected,
                indication = null,
                role = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = tab.iconImageVector,
            contentDescription = tab.description,
            tint = if (selected) {
                Color.Black
            } else {
                Color.Gray
            },
            modifier = Modifier.size(34.dp),
        )
    }
}