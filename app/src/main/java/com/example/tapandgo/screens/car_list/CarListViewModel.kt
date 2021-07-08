package com.example.tapandgo.screens.car_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.tapandgo.helpers.Smartlog
import com.example.tapandgo.repository.CarRepository
import com.example.tapandgo.repository.UserRepository
import com.example.tapandgo.utils.Resource
import com.example.tapandgo.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import java.lang.Exception

class CarListViewModel(
    private val carRepository: CarRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val error = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>(false)

    val cars = liveData(Dispatchers.IO) {
        carRepository.getCars().catch {
            error.postValue("chyba: ${it.message}")
            Smartlog.log(it.stackTraceToString())
        }.collect {
            when (it.status) {
                Status.LOADING -> {
                    loading.postValue(true)
                }
                Status.SUCCESS -> {
                    loading.postValue(false)
                }
                Status.ERROR -> {
                    loading.postValue(false)
                    error.postValue("Chyba: ${it.error?.message}")
                }
            }
            emit(it.data)
        }
    }

    val user = liveData(Dispatchers.IO) {
        userRepository.getUser().catch {
            error.postValue("User profile fetching error")
        }.collect {
            emit(it.data)
        }
    }
}