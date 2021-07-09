package com.example.tapandgo.screens.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tapandgo.helpers.EncryptedPrefs
import com.example.tapandgo.repository.UserRepository
import com.example.tapandgo.utils.BadRequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository): ViewModel() {

    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val keepSignedIn = MutableLiveData(false)

    val error = MutableLiveData<String>()
    val success = MutableLiveData<Boolean>(false)

    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                EncryptedPrefs.setKeepLoggedIn(keepSignedIn.value ?: false)
                userRepository.login(email.value ?: "", password.value ?: "")
                success.postValue(true)
            } catch (e: Exception) {
                throw e
                val errorText = when(e) {
                    is BadRequestException -> "Bad request. Please contact our support or try again later."
                    else -> "Unknown login error. Please, try again later."
                }
                error.postValue(errorText)
            }
        }
    }
}