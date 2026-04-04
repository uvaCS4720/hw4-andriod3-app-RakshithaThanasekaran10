package edu.nd.pmcburne.hello

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.nd.pmcburne.hello.data.PlaceDao
import edu.nd.pmcburne.hello.data.PlaceEntity
import edu.nd.pmcburne.hello.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

//represents the ui state for mainactivity
data class MainUIState(
    val allPlaces: List<PlaceEntity> = emptyList(),
    val selectedTag: String = "core",
    val uniqueTags: List<String> = emptyList()
)

//viewmodel for viewmainactivity handling api, database, and ui state
class MainViewModel(
    private val apiService: ApiService,
    private val placeDao: PlaceDao
) : ViewModel() {

    //backing stateflow to hold mutable ui state
    private val _uiState = MutableStateFlow(MainUIState())

    //exposed immutable stateflow for ui to observe
    val uiState: StateFlow<MainUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            // fetches API and inserts into DB safely
            try {
                val places = apiService.getPlacements()
                places.forEach { place ->
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
            } catch (e: Exception) {
                e.printStackTrace() //handles api/network errors
            }

            // Observes DB changes and updates ui state
            placeDao.getAllPlacesFlow().collectLatest { allPlaces ->
                val tags = allPlaces
                    .flatMap { it.tags.split(",") }
                    .map { it.trim() }
                    .distinct()
                    .sorted()

                _uiState.value = MainUIState(
                    allPlaces = allPlaces,
                    selectedTag = "core", //resets selected tag on refresh
                    uniqueTags = tags
                )
            }
        }
    }

    //updates selected tag in ui state
    fun selectTag(tag: String) {
        _uiState.value = _uiState.value.copy(selectedTag = tag)
    }
}
