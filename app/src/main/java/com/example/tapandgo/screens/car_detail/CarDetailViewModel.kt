package com.example.tapandgo.screens.car_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tapandgo.model.Car
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import java.util.*

class CarDetailViewModel: ViewModel() {

    val cars: MutableLiveData<List<Car>> = MutableLiveData()
    val car: MutableLiveData<Car> = MutableLiveData()

    val selectedCar = MutableLiveData<Car?>(null)
    val selectedLocation = MutableLiveData<String?>(null)
    val selectedRange = MutableLiveData<Pair<String, String>?>(null)
    val selectedRangeDate = MutableLiveData<Pair<LocalDateTime, LocalDateTime>?>(null)
}