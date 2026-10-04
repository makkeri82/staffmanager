package com.example.staffmanager.util

fun String.toDayMonth(): String {
    val datePart = substringBefore("T")
    val parts = datePart.split("-")
    if (parts.size != 3) return this
    val (_, month, day) = parts
    return "$day.$month"
}
