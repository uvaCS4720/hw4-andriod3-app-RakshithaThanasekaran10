package edu.nd.pmcburne.hello

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.nd.pmcburne.hello.data.PlaceDao
import edu.nd.pmcburne.hello.data.PlaceEntity
import edu.nd.pmcburne.hello.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MainUIState(
    val allPlaces: List<PlaceEntity> = emptyList(),
    val selectedTag: String = "core",
    val uniqueTags: List<String> = emptyList()
)

class MainViewModel(
    private val apiService: ApiService,
    private val placeDao: PlaceDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUIState())
    val uiState: StateFlow<MainUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            syncPlaces()
        }
    }

    private suspend fun syncPlaces() {
        // Fetch from API
        val placesFromApi = apiService.getPlacements()

        // Insert into DB (ignore duplicates)
        for (place in placesFromApi) {
            placeDao.insertOrIgnore(
                PlaceEntity(
                    id = place.id,
                    name = place.name,
                    description = place.description,
                    latitude = place.visualCenter.latitude,
                    longitude = place.visualCenter.longitude,
                    tags = place.tagList.joinToString(",")
                )
            )
        }

        // Get all places from DB
        val allPlaces = placeDao.getAllPlaces()

        // Extract unique tags alphabetically
        val tags = allPlaces.flatMap { it.tags.split(",") }
            .map { it.trim() }
            .distinct()
            .sorted()

        // Update UI state
        _uiState.value = MainUIState(
            allPlaces = allPlaces,
            selectedTag = "core",
            uniqueTags = tags
        )
    }

    fun selectTag(tag: String) {
        _uiState.value = _uiState.value.copy(selectedTag = tag)
    }
}