package com.example.contactformapp.audiorecorder.location

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LocationViewModel:ViewModel() {

    private var _location by mutableStateOf<LocationData?>(null)
    var location: LocationData? = _location

    fun updateLocation(newLocation: LocationData){
        location = newLocation
    }

}