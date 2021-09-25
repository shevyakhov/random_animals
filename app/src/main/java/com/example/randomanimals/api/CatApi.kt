package com.example.randomanimals.api

import retrofit2.Call
import retrofit2.http.GET

interface CatApi {
    @GET("https://thatcopy.pw/catapi/rest/")
    fun getCat(): Call<CatResponse>

}