package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.Repository.AsteroidRadarRepository

class MainViewModelFactory(private val application: Application,
                           private val asteroidRadarRepository: AsteroidRadarRepository)
                           : ViewModelProvider.AndroidViewModelFactory(application) {
    @Suppress("unchecked cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application, asteroidRadarRepository ) as T
        }
        throw IllegalArgumentException("unknown ViewModel Class")
    }
}

