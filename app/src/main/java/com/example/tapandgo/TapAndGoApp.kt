package com.example.tapandgo

import android.app.Application
import android.content.ContextWrapper
import com.example.tapandgo.helpers.EncryptedPrefs
import com.example.tapandgo.helpers.FirebaseObject
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.FirebaseDatabase
import com.jakewharton.threetenabp.AndroidThreeTen
import com.pixplicity.easyprefs.library.Prefs
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class TapAndGoApp: Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseObject.initialize(this)

        startKoin{
            androidLogger()
            androidContext(this@TapAndGoApp)
            modules(appModule)
        }

        EncryptedPrefs.initEncryptedPrefs(this)
        AndroidThreeTen.init(this)

        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }
}