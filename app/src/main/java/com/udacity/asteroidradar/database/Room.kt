package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {
    @Query("SELECT * FROM asteroidRadar WHERE closeApproachableDate >= :startDate AND closeApproachableDate <= :endDate ORDER BY closeApproachableDate ASC")
    suspend  fun  getAsteroid(startDate: String, endDate: String): List<DatabaseAsteroid>

    @Query("SELECT * FROM asteroidRadar ORDER BY closeApproachableDate ASC ")
    suspend fun getAllAsteroid(): List<DatabaseAsteroid>

    @Insert(onConflict=OnConflictStrategy.REPLACE)
     fun insertAsteroid(asteroid: List<DatabaseAsteroid>)
}

@Database(entities = [DatabaseAsteroid::class], version = 1)
abstract class AsteroidDatabase: RoomDatabase() {
    abstract val asteroidDao:AsteroidDao
}

private lateinit var INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext,
            AsteroidDatabase::class.java,
            "asteroid").build()
        }
    }
    return INSTANCE
}
