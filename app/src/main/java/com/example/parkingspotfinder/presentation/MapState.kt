package com.example.parkingspotfinder.presentation

import com.example.parkingspotfinder.domain.model.ParkingSpot
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import java.util.Properties

//it will contains combined all screens state

data class  MapState(
    val properties: MapProperties = MapProperties(),
    val parkingSpots : List<ParkingSpot> = emptyList(),
    val isfalloutMap : Boolean = false
)
