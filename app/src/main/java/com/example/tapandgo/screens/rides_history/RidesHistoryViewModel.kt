package com.example.tapandgo.screens.rides_history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.tapandgo.repository.CarRepository
import com.example.tapandgo.repository.RentalRepository
import com.example.tapandgo.repository.UserRepository
import com.example.tapandgo.utils.Resource
import com.example.tapandgo.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import java.lang.Exception

class RidesHistoryViewModel(private val rentalRepository: RentalRepository, private val carRepository: CarRepository, private val userRepository: UserRepository): ViewModel() {

    val error = MutableLiveData<Throwable>()
    val loading = MutableLiveData(false)

    val rentals = liveData(Dispatchers.IO) {
        try {
            rentalRepository.getRentals().catch {
                error.postValue(it)
            }.combine(carRepository.getCars()) { rentalsRes, carsRes ->
                val rentals = rentalsRes.data ?: emptyList()
                val cars = carsRes.data ?: emptyList()

                when {
                    rentalsRes.status == Status.LOADING || carsRes.status == Status.LOADING -> {
                        Resource.loading(rentals.flatMap { rental -> cars.map { car -> rental to car } })
                    }
                    rentalsRes.status == Status.ERROR -> {
                        Resource.error(rentalsRes.error?: Throwable("Unexpected error"), rentals.flatMap { rental -> cars.map { car -> rental to car } })
                    }
                    carsRes.status == Status.ERROR -> {
                        Resource.error(carsRes.error?: Throwable("Unexpected error"), rentals.flatMap { rental -> cars.map { car -> rental to car } })
                    }
                    rentalsRes.status == Status.SUCCESS && carsRes.status == Status.SUCCESS -> {
                        Resource.success(rentals.flatMap { rental -> cars.map { car -> rental to car } })
                    }
                    else -> {
                        Resource.success(rentals.flatMap { rental -> cars.map { car -> rental to car } })
                    }
                }
            }.collect {
                when(it.status) {
                    Status.LOADING -> loading.postValue(true)
                    Status.SUCCESS -> loading.postValue(false)
                    Status.ERROR -> {
                        error.postValue(it.error ?: Throwable("Unexpected error"))
                        loading.postValue(false)
                    }
                }
                emit(it.data)
            }
        } catch (e: Exception) {

        }
    }

    val user = liveData(Dispatchers.IO) {
        userRepository.getUser().catch {
            error.postValue(it)
        }.collect {
            emit(it.data)
        }
    }
}