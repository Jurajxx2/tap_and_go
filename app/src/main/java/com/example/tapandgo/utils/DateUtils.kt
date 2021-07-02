package com.example.tapandgo.utils

import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import java.time.LocalDate
import java.util.*

fun stringToDate(date: String): LocalDateTime {
    val instant: Instant = Instant.parse(date)
    return instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
}

fun dateToString(date: LocalDateTime): String {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    return date.format(formatter)
}

fun dateToLocalDateTime(date: Date): LocalDateTime {
    return Instant.ofEpochMilli(date.time).atZone(ZoneId.systemDefault())
        .toLocalDateTime()
}

fun localDateToLocalDate(date: LocalDate): org.threeten.bp.LocalDate {
    return org.threeten.bp.LocalDate.of(date.year, date.monthValue, date.dayOfMonth)
}