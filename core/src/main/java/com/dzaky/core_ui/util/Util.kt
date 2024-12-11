package com.dzaky.core_ui.util

fun Double?.orEmpty(value: Double = 0.0): Double = this ?: value

fun Int?.orEmpty(value: Int = 0): Int = this ?: value

fun Long?.orEmpty(value: Long = 0): Long = this ?: value

fun String.appendHttps(): String = if (this.startsWith("//")) {
    "https:$this"
} else {
    this
}