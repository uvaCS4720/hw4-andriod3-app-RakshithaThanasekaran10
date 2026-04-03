package edu.nd.pmcburne.hello

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.nd.pmcburne.hello.data.PlaceDao
import edu.nd.pmcburne.hello.network.ApiService

class MainViewModelFactory(
    private val apiService: ApiService,
    private val placeDao: PlaceDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MainViewModel(apiService, placeDao) as T
    }
}
