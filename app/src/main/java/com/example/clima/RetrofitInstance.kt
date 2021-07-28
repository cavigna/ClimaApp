package com.example.clima

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    const val BASE_URL: String = "https://api.openweathermap.org/data/2.5/"
    const val ID_CITY: String = "3875139"
    const val KEY: String ="0fd03fe3410ce2d6e4c3f44392cf5ebd"

    //"06c921750b9a82d8f5d1294e1586276f"

    val api: APIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }
}