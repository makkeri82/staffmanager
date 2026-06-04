package com.example.staffmanager.di

import androidx.compose.runtime.mutableStateOf
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

class EventSelectionState {
    var selectedEventId = mutableStateOf("")
}

fun initKoin(config: KoinAppDeclaration? = null) = startKoin {
    config?.invoke(this)
    modules(appModule)
}
