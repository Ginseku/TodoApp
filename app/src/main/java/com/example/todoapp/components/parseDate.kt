package com.example.todoapp.components

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun parseDate(dateString: String?): LocalDate? {
    if (dateString == null) return null

    val possibleFormatters = listOf(
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"), // из PostgreSQL
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),       // без миллисекунд
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),     // ISO
        DateTimeFormatter.ofPattern("yyyy-MM-dd")                 // только дата
    )

    for (formatter in possibleFormatters) {
        try {
            return try {
                LocalDateTime.parse(dateString, formatter).toLocalDate()
            } catch (_: Exception) {
                LocalDate.parse(dateString, formatter)
            }
        } catch (_: Exception) { }
    }

    return null
}
