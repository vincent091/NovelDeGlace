package com.lnproduction.noveldeglace.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun parseDateToddMMyyyy(time: String): String? {
    val inputPattern = "yyyy-MM-dd'T'HH:mm:ss"
    val outputPattern = "EEE dd MMMM yyyy HH:mm"
    val inputFormat = SimpleDateFormat(inputPattern, Locale.FRANCE)
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    val outputFormat = SimpleDateFormat(outputPattern, Locale.FRANCE)

    var date: Date? = null
    var str: String? = null

    try {
        date = inputFormat.parse(time)
        str = outputFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return str
}