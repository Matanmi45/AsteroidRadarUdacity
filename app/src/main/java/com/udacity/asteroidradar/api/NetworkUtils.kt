package com.udacity.asteroidradar.api

import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.DatabaseAsteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

fun parseAsteroidsJsonResult(jsonResult: JSONObject): ArrayList<NetworkAsteroid> {
    val nearEarthObjectsJson = jsonResult.getJSONObject("near_earth_objects")

    val asteroidList = ArrayList<NetworkAsteroid>()

    val nextSevenDaysFormattedDates = getNextSevenDaysFormattedDates()
    for (formattedDate in nextSevenDaysFormattedDates) {
        val dateAsteroidJsonArray = nearEarthObjectsJson.getJSONArray(formattedDate)

        for (i in 0 until dateAsteroidJsonArray.length()) {
            val asteroidJson = dateAsteroidJsonArray.getJSONObject(i)
            val id = asteroidJson.getLong("id")
            val codename = asteroidJson.getString("name")
            val absoluteMagnitude = asteroidJson.getDouble("absolute_magnitude_h")
            val estimatedDiameter = asteroidJson.getJSONObject("estimated_diameter")
                .getJSONObject("kilometers").getDouble("estimated_diameter_max")

            val closeApproachData = asteroidJson
                .getJSONArray("close_approach_data").getJSONObject(0)
            val relativeVelocity = closeApproachData.getJSONObject("relative_velocity")
                .getDouble("kilometers_per_second")
            val distanceFromEarth = closeApproachData.getJSONObject("miss_distance")
                .getDouble("astronomical")
            val isPotentiallyHazardous = asteroidJson
                .getBoolean("is_potentially_hazardous_asteroid")

            val asteroid = NetworkAsteroid(id, codename, formattedDate, absoluteMagnitude,
                estimatedDiameter, relativeVelocity, distanceFromEarth, isPotentiallyHazardous)
            asteroidList.add(asteroid)
        }
    }

    return asteroidList
}

private fun getNextSevenDaysFormattedDates(): ArrayList<String> {
    val formattedDateList = ArrayList<String>()

    val calendar = Calendar.getInstance()
    for (i in 0..Constants.DEFAULT_END_DATE_DAYS) {
        val currentTime = calendar.time
        var dateFormat = " "
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault()).toString() }
        formattedDateList.add(dateFormat.format(currentTime))
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }

    return formattedDateList
}

/**fun getToday(): String {
    val calendar = Calendar.getInstance()
    return formatDate(calendar.time)
}

fun getSeventhDay(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 7)
    return formatDate(calendar.time)
}
private fun formatDate(date: Date): String {
    var dateFormat = " "
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault()).toString()

    }
    return dateFormat.format(date)
}
  .
*/

@RequiresApi(Build.VERSION_CODES.O)
fun getTodayDate(): String {
    val todayDate = LocalDate.now()
    return (todayDate.format(DateTimeFormatter.ofPattern(Constants.API_QUERY_DATE_FORMAT)))

}

@RequiresApi(Build.VERSION_CODES.O)
fun getSeventhDayDate(): String {
    val todayDate = LocalDate.now().plusDays(7)
    return (todayDate.format(DateTimeFormatter.ofPattern(Constants.API_QUERY_DATE_FORMAT)))

}



suspend fun getPictureOfTheDay(): PictureOfDay? {
    var pictureOfDay: PictureOfDay
    withContext(Dispatchers.IO) {
        pictureOfDay = AsteroidApi.retrofitService.getPictureOfTheDay(API_KEY)
    }
    if (pictureOfDay.mediaType == "image") {
        return pictureOfDay
    }
    return null
}

