package com.example.randomanimals.api

import retrofit2.Call
import retrofit2.http.GET

interface DuckApi {
    @GET ("./random")
    fun getDuck():Call<DuckResponse>

}
