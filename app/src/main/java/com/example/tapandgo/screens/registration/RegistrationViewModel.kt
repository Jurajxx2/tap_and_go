package com.example.tapandgo.screens.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tapandgo.repository.UserRepository
import com.example.tapandgo.utils.BadRequestException
import com.example.tapandgo.utils.ConflictRecordException
import com.example.tapandgo.utils.mapToDomainException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationViewModel(private val userRepository: UserRepository): ViewModel() {

    val name = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")

    val error = MutableLiveData<String>()
    val success = MutableLiveData<Boolean>(false)

    fun register() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val words = name.value?.trim() ?: throw Exception()
                val wordsList = words.split("\\s+".toRegex())
                val firstName = wordsList.first()
                val lastName = wordsList.filterIndexed { index, _ -> index > 0 }.joinToString(" ")
                userRepository.register(firstName, lastName, email.value ?: "", password.value ?: "")
                success.postValue(true)
            } catch (e: Exception) {
                val errorText = when(e) {
                    is ConflictRecordException -> "User with this registration information already exists. Please use login instead."
                    is BadRequestException -> "Bad request. Please contact our support or try again later."
                    else -> "Unknown registration error. Please, try again later."
                }
                error.postValue(errorText)
            }
        }
    }
}