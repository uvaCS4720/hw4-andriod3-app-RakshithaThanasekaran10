package edu.nd.pmcburne.hello.ui.theme

import androidx.compose.runtime.Composable
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

    GoogleMap(cameraPositionState = cameraPositionState) {
        places.forEach { place ->
            Marker(
                state = MarkerState(LatLng(place.latitude, place.longitude)),
                title = place.name,
                snippet = place.description
            )
        }
    }
}