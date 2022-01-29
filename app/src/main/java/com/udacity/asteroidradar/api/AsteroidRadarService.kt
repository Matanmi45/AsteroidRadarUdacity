package com.udacity.asteroidradar.api

import android.os.Build
import androidx.annotation.RequiresApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.Constants.BASE
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.Constants.PICTURE_OF_THE_DAY
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.DatabaseAsteroid
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()




interface AsteroidRadarService {
    @GET(BASE)
    suspend fun getAsteroidRadar(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") api_key: String
    ) : String

    @GET(PICTURE_OF_THE_DAY)
    suspend fun getPictureOfTheDay(
        @Query("api_key") api_key: String
    ): PictureOfDay

}

object AsteroidApi{

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()


    val retrofitService : AsteroidRadarService by lazy {
        retrofit.create(AsteroidRadarService::class.java)
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
 //val response = AsteroidApi.retrofitService.getAsteroidRadar(getTodayDate(), getSeventhDayDate(), API_KEY )





