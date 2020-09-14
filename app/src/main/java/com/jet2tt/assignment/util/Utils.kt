package com.jet2tt.assignment.util

import android.annotation.SuppressLint
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
object Utils {

    fun abbrevCount(number: Number): String? {
        val suffix = charArrayOf(' ', 'K', 'M', 'B', 'T', 'P', 'E')
        val numValue = number.toLong()
        val value = floor(log10(numValue.toDouble())).toInt()
        val base = value / 3
        return if (value >= 3 && base < suffix.size) {
            DecimalFormat("#0.0").format(
                numValue / 10.0.pow(base * 3.toDouble())
            ) + suffix[base]
        } else {
            DecimalFormat("#,##0").format(numValue)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getTimeAgo(dateStr: String): String? {
        var convertedTime = ""

        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val past = format.parse(dateStr)

        val now = Date()

        val dateDiff = now.time - past.time

        val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
        val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
        val hours: Long = TimeUnit.MILLISECONDS.toHours(dateDiff)
        val days: Long = TimeUnit.MILLISECONDS.toDays(dateDiff)

        when {
            seconds < 60 -> {
                convertedTime = "$seconds sec"
            }
            minutes < 60 -> {
                convertedTime = "$minutes min"
            }
            hours < 24 -> {
                convertedTime = "$hours hr"
            }
            days >= 7 -> {
                convertedTime = when {
                    days > 360 -> {
                        "${(days / 360)} years"
                    }
                    days > 30 -> {
                        "${(days / 30)} months"
                    }
                    else -> {
                        "${(days / 7)} week"
                    }
                }
            }
            days < 7 -> {
                convertedTime = "$days days";
            }
        }

        return convertedTime
    }
}