package com.example.clima


import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.clima.RetrofitInstance.api
import com.example.clima.api.ClimaJson
import com.example.clima.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.TimeZone.*


const val BASE_URL: String = "https://api.openweathermap.org/data/2.5/"
const val ID_CITY: String = "3875139"
const val KEY: String = "06c921750b9a82d8f5d1294e1586276f"
//const val k : String = KeyApi

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    val sdf: SimpleDateFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
    var control: Boolean = false





    private lateinit var binding: ActivityMainBinding
    //val KEY: String = "0fd03fe3410ce2d6e4c3f44392cf5ebd"


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.temperatura.visibility = View.INVISIBLE
        searchById()
//        if (control) {
//            binding.temperatura.visibility = View.VISIBLE
//        }


    }



    private fun searchById() {


        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<ClimaJson> =

                api.getClimaByID(
                    "weather?id=${RetrofitInstance.ID_CITY}" +
                            "&appid=${RetrofitInstance.KEY}&units=metric&lang=es"
                )

            val data = call.body()!!

            if (call.isSuccessful) {

                var temp: String
                var ubicacion: String
                var minTemp: String
                var maxTemp: String
                var condicion: String
                var humedad: String
                var presion: String



                withContext(Dispatchers.Main) {
                    temp = data.main.temp.toInt().toString()
                    condicion = data.weather.get(0).description.capitalize()
                    minTemp = data.main.temp_min.toInt().toString()
                    maxTemp = data.main.temp_max.toInt().toString()
                    ubicacion = data.name.capitalize()
                    humedad = data.main.humidity.toString()
                    presion = data.main.pressure.toString()
                    val viento: String = data.wind.speed.toString()
                    val sensacion: String = data.main.feels_like.toInt().toString()
                    val sunrise: Long = data.sys.sunrise.toLong()
                    val sunset: Long = data.sys.sunset.toLong()
                    val tz: TimeZone = getTimeZone("GMT-0400")
                    sdf.timeZone = tz

                    binding.temperatura.text = "$temp°C"
                    binding.textViewCondition.text = condicion
                    binding.textViewMinTemp.text = "Mínima: $minTemp°C"
                    binding.textViewMaxTemp.text = "Máxima: $maxTemp°C"
                    binding.textViewUbicacion.text = ubicacion
                    binding.humedad.text = "$humedad%"
                    binding.presion.text = "$presion Hpa"
                    binding.viento.text = "$viento Km/h"
                    binding.sensacion.text = "$sensacion°C"
                    binding.sunrise.text = sdf.format(Date(sunrise * 1000))
                    binding.sunset.text = sdf.format(Date(sunset * 1000))

                    Log.e("Salió", data.main.temp.toString())
                    control = true
                }
            } else {
                mostrarError()
            }
        }
    }

    private fun mostrarError() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        Log.d("Err0r", "Error")
    }


}

//https://api.openweathermap.org/data/2.5/weather?id=3875139&appid=06c921750b9a82d8f5d1294e1586276f



