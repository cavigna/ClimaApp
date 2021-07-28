package com.example.clima

import com.example.clima.api.ClimaJson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getClimaByID(@Url ulr: String): Response<ClimaJson>
}