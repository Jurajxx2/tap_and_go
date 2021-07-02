package com.example.tapandgo.screens.picker_location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tapandgo.model.Car
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.osmdroid.bonuspack.location.GeocoderNominatim

class PickerLocationViewModel: ViewModel() {

    val cars = MutableLiveData<List<Car>>()
    val selectedCar = MutableLiveData<Car?>(null)
    val selectedLocation = MutableLiveData<String?>(null)

    val locations = MutableLiveData<List<Pair<String, Car>>>()

    fun getLocationFromCoordinates(cars: List<Car>) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = cars.map { car ->
                val addresses = GeocoderNominatim(System.getProperty("http.agent")).getFromLocation(car.location.latitude, car.location.longitude, 1)
                var address = ""
                address += if (addresses.firstOrNull()?.thoroughfare.isNullOrEmpty()) "Unknown street" else addresses.firstOrNull()?.thoroughfare
                address += if (addresses.firstOrNull()?.subThoroughfare.isNullOrEmpty()) "" else " ${addresses.firstOrNull()?.subThoroughfare}"
                address += if (addresses.firstOrNull()?.adminArea.isNullOrEmpty()) "" else ", ${addresses.firstOrNull()?.adminArea}"
                address to car
            }
            locations.postValue(result)
        }
    }
}