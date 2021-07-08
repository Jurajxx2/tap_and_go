package com.example.tapandgo.screens.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tapandgo.helpers.EncryptedPrefs
import com.example.tapandgo.repository.UserRepository

class SplashViewModel(private val userRepository: UserRepository): ViewModel() {

    val loggedIn = MutableLiveData(false)

    init {
        isLoggedIn()
    }

    private fun isLoggedIn() {
        val token1 = EncryptedPrefs.getToken()
        val token2 = EncryptedPrefs.getRefreshToken()

        if (!token1.isNullOrEmpty() && !token2.isNullOrEmpty()) {
            loggedIn.postValue(true)
        }
    }
}