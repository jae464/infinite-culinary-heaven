package com.jae464.presentation

import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

fun LocalDate.toFormattedString(): String {
    val year = this.year
    val month = this.monthValue

    val weekFields = WeekFields.of(Locale.getDefault())
    val weekOfMonth = this.get(weekFields.weekOfMonth())

    return "${year}년 ${month}월 ${weekOfMonth}주차"
}