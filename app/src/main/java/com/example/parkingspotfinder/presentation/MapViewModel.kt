package com.example.parkingspotfinder.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkingspotfinder.domain.model.ParkingSpot
import com.example.parkingspotfinder.domain.repository.ParkingSpotRepository
import com.google.android.gms.maps.model.MapStyleOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val repository: ParkingSpotRepository) : ViewModel() {

    var state by mutableStateOf(MapState())

    init {
        viewModelScope.launch {
            repository.getParkingSpots().collectLatest { spots->
                state = state.copy(
                    parkingSpots = spots
                )
            }
        }
    }

    fun onEvent(event: MapEvent){
        when(event){
            is MapEvent.ToggleFalloutMap -> {
                state = state.copy(
                    properties = state.properties.copy(
                        mapStyleOptions = if(state.isfalloutMap)
                        {
                            null
                        }else
                        {
                            MapStyleOptions(MapStyle.json)
                        },
                    ), isfalloutMap = !state.isfalloutMap
                )
            }
            is MapEvent.OnMapLongClick -> {
                viewModelScope.launch {
                    repository.insertParkingSpot(ParkingSpot(
                        event.latLng.latitude,
                        event.latLng.longitude
                    ))
                }
            }
            is MapEvent.OnInfoWindowLongClick -> {
                viewModelScope.launch {
                    repository.deleteParkingSpot(event.spot)
                }
            }
        }
    }

}