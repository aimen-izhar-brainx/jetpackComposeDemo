package com.example.repconnectjetpackcompose.utils

import android.content.Context
import android.text.format.DateUtils
import com.example.repconnectjetpackcompose.R
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun getNotificationAgoTime(context: Context, date: Date?): String {
        if (date == null) return ""

        val now = Calendar.getInstance()
        val timeAgo = (now.timeInMillis - date.time)
        return when {
            timeAgo < DateUtils.MINUTE_IN_MILLIS ->
                context.getString(R.string.just_now)
            timeAgo / DateUtils.DAY_IN_MILLIS.toFloat() <= 6 ->
                DateUtils.getRelativeTimeSpanString(date.time) as String
            else ->
                SimpleDateFormat("yyyy-MM-dd", Locale.US).format(date)
        }
    }

    fun String.formatString(): Date {
        val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z")
        return dateFormat.parse(this)
    }

    fun downloadXml(url: String): String {
        val connection = URL(url).openConnection() as HttpURLConnection
        try {
            connection.inputStream.use { input ->
                BufferedReader(InputStreamReader(input)).use { reader ->
                    val result = StringBuilder()
                    var line = reader.readLine()
                    while (line != null) {
                        result.append(line)
                        line = reader.readLine()
                    }
                    return result.toString()
                }
            }
        } finally {
            connection.disconnect()
        }
    }
}
