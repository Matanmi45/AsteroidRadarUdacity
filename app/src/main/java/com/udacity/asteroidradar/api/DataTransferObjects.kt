package com.udacity.asteroidradar.api

import com.squareup.moshi.Json
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.DatabaseAsteroid
import com.udacity.asteroidradar.database.PictureOfTheDayDatabase

data class NetworkAsteroid constructor (
    val id: Long,
    val codename: String,
    val closeApproachableDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyDangerous: Boolean
        )

data class PictureOfTheDayNetwork(
    var title: String,
    @Json(name = "media_type")
    val mediaType: String,
    val url: String

)

fun PictureOfTheDayNetwork.asDatabaseModel(): PictureOfTheDayDatabase{
    return let {
        PictureOfTheDayDatabase(
            id = 1,
            url = it.url,
            mediaType = it.mediaType,
            title = it.title

        )
    }
}


fun List<NetworkAsteroid>.asDomainModel(): List<Asteroid>{
    return map{
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
    }
}

fun List<NetworkAsteroid>.asDatabaseModel(): List<DatabaseAsteroid> {
    return map{
        DatabaseAsteroid(
            id = it.id,
            codename= it.codename,
            closeApproachableDate = it.closeApproachableDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
           isPotentiallyDangerous = it.isPotentiallyDangerous
        )
    }
}



