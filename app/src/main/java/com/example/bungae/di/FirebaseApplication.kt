package com.example.bungae.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FirebaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}