package com.example.locationapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.locationapp.data.LocationData

class LocationViewmodel: ViewModel() {
    private var _location  = mutableStateOf<LocationData?>(null)
    var location: State<LocationData?> = _location
        fun getLocation(newLocationData: LocationData)
        {
            _location.value = newLocationData
        }
}