package com.example.tapandgo.utils

import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

fun stringToDate(date: String): LocalDateTime {
    val instant: Instant = Instant.parse(date)
    return instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
}

fun dateToLocalDate(date: Date): LocalDate {
    return Instant.ofEpochMilli(date.time).atZone(ZoneId.systemDefault())
        .toLocalDate()
}

fun localDateToString(date: LocalDate): String {
    val dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX")
    return date.atStartOfDay().atOffset(ZoneOffset.UTC).format(dtf)
}

fun localDateAtTheEndOfDayToString(date: LocalDate): String {
    val dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX")
    return date.atTime(23,59,59).atOffset(ZoneOffset.UTC).format(dtf)
}