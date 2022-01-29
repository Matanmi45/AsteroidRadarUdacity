package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import okhttp3.MediaType

@Entity(tableName = "asteroidRadar")
data class DatabaseAsteroid constructor(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachableDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyDangerous: Boolean
    )

@Entity
data class PictureOfTheDayDatabase(
    @PrimaryKey
    val id: Long,
    val url: String,
    val mediaType: String,
    val title: String
)

fun PictureOfTheDayDatabase.asDomainModel(): PictureOfDay{
    return let {
        PictureOfDay(
            url = it.url,
            mediaType = it.mediaType,
            title = it.title
        )

    }
}

 fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
     return ( map {
         Asteroid(
             id = it.id,
             codename= it.codename,
             closeApproachDate = it.closeApproachableDate,
             absoluteMagnitude = it.absoluteMagnitude,
             estimatedDiameter = it.estimatedDiameter,
             relativeVelocity = it.relativeVelocity,
             distanceFromEarth = it.distanceFromEarth,
             isPotentiallyHazardous = it.isPotentiallyDangerous
         )
     })
 }
