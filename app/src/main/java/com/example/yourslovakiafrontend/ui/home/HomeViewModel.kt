package com.example.yourslovakiafrontend.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourslovakiafrontend.api_handler.ApiHandler
import fiit.mtaa.yourslovakia.models.PointOfInterest
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    val pointsOfInterest = MutableLiveData<List<PointOfInterest>>()

    fun loadPointsOfInterest(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val types = listOf("cave", "zoo", "lake", "reservoir") // Define as needed
            val pois = ApiHandler.getPointsOfInterest(latitude, longitude, 300000, types)
            pointsOfInterest.postValue(pois ?: listOf())
        }
    }
}
