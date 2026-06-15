package com.example.staffmanager.di

import com.liftric.kvault.KVault
import org.koin.dsl.module

val iosModule = module {
    single { KVault(serviceName = "auth_vault") }
}
