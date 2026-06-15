package com.example.staffmanager.di

import com.liftric.kvault.KVault
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {
    single { KVault(androidContext(), "auth_vault") }
}
