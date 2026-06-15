package com.example.staffmanager

import android.app.Application
import org.koin.android.ext.koin.androidContext
import com.example.staffmanager.di.androidModule
import com.example.staffmanager.di.initKoin

class StaffManagerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(androidModule) {
            androidContext(this@StaffManagerApplication)
        }
    }
}