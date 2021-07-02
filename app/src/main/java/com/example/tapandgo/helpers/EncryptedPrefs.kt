package com.example.tapandgo.helpers

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.tapandgo.model.Profile
import java.util.concurrent.TimeUnit

object EncryptedPrefs {
    lateinit var encryptedPrefs: SharedPreferences

    private const val ACCESS_TOKEN = "access_token"
    private const val REFRESH_TOKEN = "refresh_token"
    private const val LAST_REFRESH = "last_refresh"

    fun initEncryptedPrefs(context: Context) {
        val key = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        encryptedPrefs =
            EncryptedSharedPreferences.create(
                context,
                "secret_shared_prefs",
                key,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
    }

    fun setToken(token: String) {
        encryptedPrefs.edit().putString(ACCESS_TOKEN, token).commit()
    }

    fun getToken(): String? {
        return encryptedPrefs.getString(ACCESS_TOKEN, null)
    }

    fun setRefreshToken(token: String) {
        encryptedPrefs.edit().putString(REFRESH_TOKEN, token).apply()
    }

    fun getRefreshToken(): String? {
        return encryptedPrefs.getString(REFRESH_TOKEN, null)
    }

    fun setLastVerification() {
        val time = System.currentTimeMillis()
        encryptedPrefs.edit().putLong(LAST_REFRESH, time).apply()
    }

    fun shouldVerify(): Boolean {
        val hourInMillis = 60*60*1000
        val lastVerified = encryptedPrefs.getLong(LAST_REFRESH, System.currentTimeMillis())
        return lastVerified + hourInMillis < System.currentTimeMillis()
    }
}