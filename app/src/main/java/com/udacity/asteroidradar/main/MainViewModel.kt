package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.Repository.AsteroidRadarRepository
import com.udacity.asteroidradar.api.getPictureOfTheDay
import com.udacity.asteroidradar.api.getSeventhDayDate
import com.udacity.asteroidradar.api.getTodayDate
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.database.getDatabase
import kotlinx.coroutines.launch

class MainViewModel(application: Application, asteroidRadarRepository: AsteroidRadarRepository) : AndroidViewModel(application) {

    private val asteroidRadarRepository = AsteroidRadarRepository(getDatabase(application))

    private val _properties = MutableLiveData<List<Asteroid>>()
    val properties: LiveData<List<Asteroid>>
    get() = _properties



    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
    get() = _pictureOfDay

    private val _navigateToSelectedProperty = MutableLiveData<Asteroid>()
    val navigateToSelectedProperty : LiveData<Asteroid>
        get() = _navigateToSelectedProperty


    init {
        getAWeekAsteroid()
        viewModelScope.launch {
            try {
                asteroidRadarRepository.refreshAsteroid()
                refreshPictureOfDay()
            }catch(e: Exception) {
                println("error refreshing data:  $e.message")
            }
        }
    }

    private fun refreshPictureOfDay(){
        viewModelScope.launch {
            _pictureOfDay.value = getPictureOfTheDay() }

    }


    fun getAWeekAsteroid() {
        viewModelScope.launch{
            //val prop = asteroidRadarRepository.getAWeekAsteroid()
            _properties.value =  asteroidRadarRepository.getAWeekAsteroid()
        }
    }

    fun getADayAsteroid() {
        viewModelScope.launch{
            _properties.value =  asteroidRadarRepository.getADayAsteroid()
        }
    }


    fun getAllAsteroid() {
        viewModelScope.launch{
            _properties.value =  asteroidRadarRepository.getAllAsteroid()
        }
    }

    fun displayAsteroidDetails(asteroid: Asteroid){
        _navigateToSelectedProperty.value = asteroid
    }

    fun displayAsteroidDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }











}