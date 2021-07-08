package com.example.tapandgo.screens.information

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.tapandgo.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class InformationViewModel(private val userRepository: UserRepository): ViewModel() {

    val error = MutableLiveData<String>()

    val user = liveData(Dispatchers.IO) {
        userRepository.getUser().catch {
            error.postValue("User profile fetching error")
        }.collect {
            emit(it.data)
        }
    }
}