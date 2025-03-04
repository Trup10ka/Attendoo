package com.trup10ka.attendoo.util

import java.time.LocalDateTime

fun convertToKotlinxLocalDateTime(dateTime: kotlinx.datetime.LocalDateTime): LocalDateTime
{
    return LocalDateTime.of(
        dateTime.year,
        dateTime.monthNumber,
        dateTime.dayOfMonth,
        dateTime.hour,
        dateTime.minute,
        dateTime.second,
        dateTime.nanosecond
    )
}
