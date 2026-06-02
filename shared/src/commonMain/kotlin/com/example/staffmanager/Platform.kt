package com.example.staffmanager

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform