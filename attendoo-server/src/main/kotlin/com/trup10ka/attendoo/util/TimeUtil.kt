package com.trup10ka.attendoo.util

import kotlinx.datetime.Instant
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDate
import java.time.LocalDateTime

fun kotlinx.datetime.LocalDateTime.convertToJavaDateTime(): LocalDateTime
{
    return LocalDateTime.of(
        this.year,
        this.monthNumber,
        this.dayOfMonth,
        this.hour,
        this.minute,
        this.second,
        this.nanosecond
    )
}

fun LocalDateTime.convertToKotlinxDateTime(): kotlinx.datetime.LocalDateTime
{
    return kotlinx.datetime.LocalDateTime(
        this.year,
        this.monthValue,
        this.dayOfMonth,
        this.hour,
        this.minute,
        this.second,
        this.nano
    )
}

fun kotlinx.datetime.LocalDate.convertToJavaDate(): LocalDate
{
    return LocalDate.of(
        this.year,
        this.monthNumber,
        this.dayOfMonth
    )
}

fun LocalDate.convertToKotlinxDate(): kotlinx.datetime.LocalDate
{
    return kotlinx.datetime.LocalDate(
        this.year,
        this.monthValue,
        this.dayOfMonth
    )
}

fun Instant.toKotlinxDateTime(): kotlinx.datetime.LocalDateTime?
{
    return this.toLocalDateTime(kotlinx.datetime.TimeZone.UTC)
}

fun Instant.toJavaDateTime(): LocalDateTime?
{
    return this.toKotlinxDateTime()?.convertToJavaDateTime()
}
