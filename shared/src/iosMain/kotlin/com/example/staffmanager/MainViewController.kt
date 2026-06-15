package com.example.staffmanager

import androidx.compose.ui.window.ComposeUIViewController
import com.example.staffmanager.di.initKoin
import com.example.staffmanager.di.iosModule

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin(iosModule)
    }
) { App() }
