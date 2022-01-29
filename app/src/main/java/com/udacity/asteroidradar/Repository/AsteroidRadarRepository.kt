package com.udacity.asteroidradar.Repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.database.DatabaseAsteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRadarRepository (private val database: AsteroidDatabase) {

    @RequiresApi(Build.VERSION_CODES.O)
    val startDate: String = getTodayDate()
    @RequiresApi(Build.VERSION_CODES.O)
    val endDate: String = getSeventhDayDate()

   //val asteroidRadarList = database.asteroidDao.getAsteroid(startDate, endDate)



    suspend fun getAWeekAsteroid (): List<Asteroid> {
        var asteroidRadarList: List<DatabaseAsteroid>
        withContext(Dispatchers.IO){
             asteroidRadarList = database.asteroidDao.getAsteroid(startDate, endDate)
        }
        return asteroidRadarList.asDomainModel()
    }

    suspend fun getADayAsteroid (): List<Asteroid> {
        var asteroidRadarList: List<DatabaseAsteroid>
        withContext(Dispatchers.IO){
            asteroidRadarList = database.asteroidDao.getAsteroid(startDate, startDate)
        }
        return asteroidRadarList.asDomainModel()
    }

    suspend fun getAllAsteroid(): List<Asteroid> {
        var asteroidRadarList: List<DatabaseAsteroid>
        withContext(Dispatchers.IO) {
            asteroidRadarList = database.asteroidDao.getAllAsteroid()
        }
        return asteroidRadarList.asDomainModel()
    }

    suspend fun refreshAsteroid () {
        withContext(Dispatchers.IO) {
            val asteroidReply = AsteroidApi.retrofitService.getAsteroidRadar(startDate, endDate, API_KEY)

            Log.e("  from retrofit service", asteroidReply)

            val asteroidList: List<NetworkAsteroid> = parseAsteroidsJsonResult(JSONObject(asteroidReply))
            database.asteroidDao.insertAsteroid(asteroidList.asDatabaseModel())
        }
    }

}



