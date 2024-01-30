package com.wolt.preassignment2024.delivery.utils

import java.time.DayOfWeek
import java.time.LocalTime
import java.time.ZonedDateTime

object TimeUtils {
    /**
     * Parses the date in string value and retrieves the current day of the week.
     *
     * This function uses [ZonedDateTime] to get the current date and time
     * and then extracts the day of the week using the [ZonedDateTime.getDayOfWeek] property.
     * The [DayOfWeek] enum is returned, representing the current day in numeric value from 1 to 7.
     *
     * @param [date] to parse such as "2024-01-26T19:13:00Z"
     * @return [DayOfWeek] enum representing the current day of the week in numeric value from 1 to 7.
     *
     */
    fun getCurrentWeekday(date: String): DayOfWeek {
        return ZonedDateTime.parse(date).dayOfWeek
    }

    /**
     * Checks if the current time is between the specified hours.
     *
     * @param [date] to parse such as "2024-01-26T19:13:00Z"
     * @param startHour The starting hour of the range in 24-hour format.
     * @param endHour The ending hour of the range in 24-hour format.
     * @return `true` if the current hour is within the specified range, `false` otherwise.
     */
    fun isDateBetweenHours(date: String, startHour: Int, endHour: Int): Boolean {
        val currentDate = ZonedDateTime.parse(date)
        val startDate = currentDate.with(LocalTime.of(startHour, 0))
        val endDate = currentDate.with(LocalTime.of(endHour, 0))

        // Adjust the endDate to the next day if startHour is greater than endHour
        // This way, the comparison correctly covers the time range that crosses midnight to the next day
        val adjustedEndDate = if (startHour > endHour) endDate.plusDays(1) else endDate

        return currentDate.isAfter(startDate) && currentDate.isBefore(adjustedEndDate)
    }
}