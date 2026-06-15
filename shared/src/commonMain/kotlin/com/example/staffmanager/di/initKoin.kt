package com.example.staffmanager.di

import androidx.compose.runtime.mutableStateOf
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

class EventSelectionState {
    var selectedEventId = mutableStateOf("")
}

fun initKoin(platformModule: Module = module {}, config: KoinAppDeclaration? = null) = startKoin {
    config?.invoke(this)
    modules(platformModule, appModule, viewModule)
}
