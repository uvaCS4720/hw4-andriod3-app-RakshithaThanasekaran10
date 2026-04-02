package edu.nd.pmcburne.hello.ui.theme

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import edu.nd.pmcburne.hello.data.PlaceEntity

@Composable
fun MapViewScreen(places: List<PlaceEntity>) {
    val defaultPosition = places.firstOrNull()?.let {
        LatLng(it.latitude, it.longitude)
    } ?: LatLng(38.03474, -78.50820)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultPosition, 15f)
    }

    LaunchedEffect(places) {
        if (places.isNotEmpty()) {
            val firstPlace = places.first()
            Log.d("MapDebug", "Camera moving to: ${firstPlace.name} = ${firstPlace.latitude}, ${firstPlace.longitude}")
            cameraPositionState.move(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.fromLatLngZoom(
                        LatLng(firstPlace.latitude, firstPlace.longitude),
                        15f
                    )
                )
            )
        }
    }

    GoogleMap(cameraPositionState = cameraPositionState) {
        places.forEach { place ->
            Log.d("MapDebug", "Adding marker: ${place.name} = ${place.latitude}, ${place.longitude}")
            Marker(
                state = MarkerState(LatLng(place.latitude, place.longitude)),
                title = place.name,
                snippet = place.description
            )
        }
    }
}