package com.example.tapandgo.screens.car_detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.tapandgo.model.Car
import com.example.tapandgo.repository.RentalRepository
import com.example.tapandgo.repository.UserRepository
import com.example.tapandgo.utils.localDateToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.osmdroid.bonuspack.location.GeocoderNominatim
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import java.util.*

class CarDetailViewModel(private val rentalRepository: RentalRepository, private val userRepository: UserRepository): ViewModel() {

    val car: MutableLiveData<Car> = MutableLiveData()
    val selectedLocation = MutableLiveData<String>("")
    val selectedRange = MutableLiveData<Pair<String, String>?>(null)
    val selectedRangeLocalDate = MutableLiveData<Pair<LocalDate, LocalDate>?>(null)

    val success = MutableLiveData(false)
    val loading = MutableLiveData(false)
    val error = MutableLiveData<String>()

    fun getLocationFromCoordinates(car: Car) {
        viewModelScope.launch(Dispatchers.IO) {
            val addresses = GeocoderNominatim(System.getProperty("http.agent")).getFromLocation(car.location.latitude, car.location.longitude, 1)
            var address = ""
            address += if (addresses.firstOrNull()?.thoroughfare.isNullOrEmpty()) "Unknown street" else addresses.firstOrNull()?.thoroughfare
            address += if (addresses.firstOrNull()?.subThoroughfare.isNullOrEmpty()) "" else " ${addresses.firstOrNull()?.subThoroughfare}"
            address += if (addresses.firstOrNull()?.adminArea.isNullOrEmpty()) "" else ", ${addresses.firstOrNull()?.adminArea}"
            selectedLocation.postValue(address)
        }
    }

    fun sendOrder() {
        viewModelScope.launch {
            try {
                loading.postValue(true)
                val carId = car.value?.id ?: throw Exception("car id value is missing")
                val from = localDateToString(selectedRangeLocalDate.value?.first ?: throw Exception("from value is missing"))
                val to = localDateToString(selectedRangeLocalDate.value?.second ?: throw Exception("to value is missing"))
                rentalRepository.createRental(carId, from, to)
                loading.postValue(false)
                success.postValue(true)
            } catch (e: Exception) {
                loading.postValue(false)
                error.postValue("There was an error while creating a rental")
            }
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