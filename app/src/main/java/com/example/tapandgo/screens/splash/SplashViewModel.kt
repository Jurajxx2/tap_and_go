package com.example.tapandgo.screens.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tapandgo.helpers.EncryptedPrefs
import com.example.tapandgo.repository.UserRepository
import kotlinx.coroutines.launch

class SplashViewModel(private val userRepository: UserRepository): ViewModel() {

    val loggedIn = MutableLiveData(false)

    init {
        isLoggedIn()
    }

    private fun isLoggedIn() {
        viewModelScope.launch {
            if (EncryptedPrefs.shouldDeleteData()) {
                userRepository.logout()
            } else {
                val token1 = EncryptedPrefs.getToken()
                val token2 = EncryptedPrefs.getRefreshToken()

                if (!token1.isNullOrEmpty() && !token2.isNullOrEmpty()) {
                    loggedIn.postValue(true)
                }
            }
        }

    }
}