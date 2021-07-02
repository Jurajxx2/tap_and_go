package com.example.tapandgo.screens.car_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.tapandgo.repository.CarRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import java.lang.Exception

class CarListViewModel(private val carRepository: CarRepository): ViewModel() {

    val error = MutableLiveData<String>()

    val cars = liveData {
        try {
            carRepository.getCars().catch {
                val errorX = it
                error.postValue("chyba: ${errorX.message}")
            }.collect {
                emit(it)
            }
        } catch (e: Exception) {
            error.postValue("chyba2")
        }
    }
}