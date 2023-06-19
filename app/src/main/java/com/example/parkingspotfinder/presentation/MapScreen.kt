package com.example.parkingspotfinder.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    viewModel: MapViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
)
{
    val snackbarHostState = remember{ SnackbarHostState()}
    val uiSettings = remember{ MapUiSettings(zoomControlsEnabled = false)}


    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState)},floatingActionButton = {
        FloatingActionButton(onClick = { viewModel.onEvent(MapEvent.ToggleFalloutMap) }) {
            Icon(imageVector = if(viewModel.state.isfalloutMap) Icons.Default.ToggleOff else Icons.Default.ToggleOn, contentDescription = "Toggle fallout map")
        }
    }) {
        GoogleMap(modifier= Modifier.fillMaxSize(), properties = viewModel.state.properties,
            uiSettings = uiSettings, onMapLongClick = {
                viewModel.onEvent(MapEvent.OnMapLongClick(it))
        } )
        {
            viewModel.state.parkingSpots.forEach { spot->
                Marker(position = LatLng(spot.lat,spot.lng),
                    title = "Parking spot (${spot.lat},${spot.lng})",
                    snippet = "Long click to delete",
                    onInfoWindowClick = {
                        viewModel.onEvent(MapEvent.OnInfoWindowLongClick(spot))},
                        onClick = {
                            it.showInfoWindow()
                            true
                        },
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            }
        }
    }
}