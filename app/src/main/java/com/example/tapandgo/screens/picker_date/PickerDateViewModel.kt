package com.example.tapandgo.screens.picker_date

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tapandgo.model.Car
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import java.util.*

class PickerDateViewModel: ViewModel() {

    val selectedCar = MutableLiveData<Car>()
    val unavailableDays = MutableLiveData<List<LocalDateTime>>()
    val selectedRange = MutableLiveData<Pair<String, String>?>(null)
    val selectedRangeLocalDate = MutableLiveData<Pair<LocalDateTime, LocalDateTime>?>(null)
}