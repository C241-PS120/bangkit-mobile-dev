package com.example.coffeeprotectionandanalysissystem.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeeprotectionandanalysissystem.response.WeatherResponse
import com.example.coffeeprotectionandanalysissystem.service.ApiConfig
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _weatherResponse = MutableLiveData<WeatherResponse?>()
    val weatherResponse: MutableLiveData<WeatherResponse?> = _weatherResponse

    fun fetchWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val apiKey = "04c55257da7d494db7012929240906"
                val location = "$latitude,$longitude"
                val response = ApiConfig.weatherService.getCurrentWeather(apiKey, location, "no")
                _weatherResponse.postValue(response)
                Log.d("WeatherAPI", "Weather API called successfully")
            } catch (e: Exception) {
                _weatherResponse.postValue(null)
                Log.e("WeatherAPI", "Error calling Weather API", e)
            }
        }
    }
}
