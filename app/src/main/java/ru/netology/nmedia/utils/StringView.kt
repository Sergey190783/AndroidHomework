package ru.netology.nmedia.utils

import kotlin.math.truncate

fun changeCountString(count: Int, change: Int): String {
    val likes = count + change
    return when {
        likes >= 1000000 && likes % 1000000 in 0..99999 -> "${likes / 1000000}M"
        likes >= 1100000 -> "${(likes / 1000000.0).roundTo1Digit()}M"
        likes >= 10000 -> "${likes / 1000}K"
        likes <= 999 -> "$likes"
        likes >= 1000 && likes % 1000 in 0..99 -> "${likes / 1000}K"
        likes >= 1000 -> "${(likes / 1000.0).roundTo1Digit()}K"
        else -> {
            ""
        }
    }
}

fun Double.roundTo1Digit(): Double {
    return truncate(this * 10.0) / 10.0
}
