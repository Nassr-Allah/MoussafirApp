package com.ems.moussafirdima.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun getCurrentDate(time: String) : String {
    val calendar = GregorianCalendar.getInstance()
    return if (getCurrentTime() < time) {
        "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
    } else {
        "${calendar.get(Calendar.DAY_OF_MONTH) + 1}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
    }
}

fun getCurrentDay() : String {
    val calendar = GregorianCalendar.getInstance()
    return if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
        "0${calendar.get(Calendar.DAY_OF_MONTH)}"
    } else {
        "${calendar.get(Calendar.DAY_OF_MONTH)}"
    }
}

fun getCurrentMonth() : String {
    val calendar = GregorianCalendar.getInstance()
    val month = if (calendar.get(Calendar.MONTH) + 1 > 12) {
        1
    } else {
        calendar.get(Calendar.MONTH) + 1
    }
    return if (month < 10) {
        "0$month"
    } else {
        "$month"
    }
}

fun getCurrentTime() : String {
    val calendar = Calendar.getInstance()
    return "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"
}

fun isDateMoreThanOneDay(date: String) : Boolean {
    val calendar = Calendar.getInstance()
    val maxDate = "${calendar.get(Calendar.DAY_OF_MONTH) + 1}/${getCurrentMonth()}/${calendar.get(Calendar.YEAR)}"
    Log.d("DateComparison", "$date and $maxDate")
    return date > maxDate
}

fun formatDate(number: Int) : String {
    return if (number < 10) {
        "0$number"
    } else {
        "$number"
    }
}