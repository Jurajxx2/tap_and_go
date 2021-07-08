package com.example.tapandgo.screens.picker_date

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.threeten.bp.LocalDate

class PickerDateViewModel: ViewModel() {

    val selectedRange = MutableLiveData<Pair<String, String>?>(null)
    val selectedRangeLocalDate = MutableLiveData<Pair<LocalDate, LocalDate>?>(null)
}