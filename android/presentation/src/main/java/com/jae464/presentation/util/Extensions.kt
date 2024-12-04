package com.jae464.presentation.util

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import java.time.LocalDateTime
import java.time.temporal.WeekFields
import java.util.Locale

fun Modifier.addFocusCleaner(focusManager: FocusManager, doOnClear: () -> Unit = {} ): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures {
            doOnClear()
            focusManager.clearFocus()
        }
    }
}

fun LocalDateTime.toFormattedString(): String {
    val year = this.year
    val month = this.monthValue

    val weekFields = WeekFields.of(Locale.getDefault())
    val weekOfMonth = this.get(weekFields.weekOfMonth())

    return "${year}년 ${month}월 ${weekOfMonth}주차"
}