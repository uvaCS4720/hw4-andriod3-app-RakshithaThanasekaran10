package edu.nd.pmcburne.hello

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import edu.nd.pmcburne.hello.data.AppDatabase
import edu.nd.pmcburne.hello.network.ApiService
import edu.nd.pmcburne.hello.ui.theme.MapViewScreen
import edu.nd.pmcburne.hello.ui.theme.MyApplicationTheme
import edu.nd.pmcburne.hello.ui.theme.TagDropdown

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            ApiService.create(),
            AppDatabase.getDatabase(this).placeDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val uiState by viewModel.uiState.collectAsState()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        TagDropdown(
                            selectedTag = uiState.selectedTag,
                            tags = uiState.uniqueTags,
                            onTagSelected = { viewModel.selectTag(it) }
                        )
                        MapViewScreen(
                            places = uiState.allPlaces.filter { place ->
                                place.tags.split(",").map { it.trim() }
                                    .contains(uiState.selectedTag)
                            }
                        )
                    }
                }
            }
        }
    }
}
