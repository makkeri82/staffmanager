package com.example.staffmanager

import androidx.compose.ui.window.ComposeUIViewController
import com.example.staffmanager.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }