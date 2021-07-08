package com.example.tapandgo.screens.logout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tapandgo.repository.CarRepository
import com.example.tapandgo.repository.RentalRepository
import com.example.tapandgo.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class LogoutBottomSheetDialogViewModel(private val userRepository: UserRepository, private val carRepository: CarRepository, private val rentalRepository: RentalRepository): ViewModel() {

    val success = MutableLiveData(false)
    val error = MutableLiveData<Throwable>()

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRepository.logout()
                success.postValue(true)
            } catch (e: Exception) {
                error.postValue(e)
            }
        }
    }
}