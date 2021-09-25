package com.example.randomanimals.api.mvvm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.randomanimals.api.CatApi
import com.example.randomanimals.api.CatResponse
import com.example.randomanimals.api.DuckApi
import com.example.randomanimals.api.DuckResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
/*retrofit logic
*
*
* TODO Not sending url properly
* */
class RetrofitService() {
    private lateinit var duckApi: DuckApi
    private lateinit var catApi: CatApi
    var responseDuck: String = ""
    var responseCat: String = ""

    fun initDuckRetrofit() {
        val okHttpClient = OkHttpClient.Builder()
            .build()
        val retrofit = Retrofit.Builder().baseUrl("https://random-d.uk/api/").client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
        duckApi = retrofit.create(DuckApi::class.java)
    }

    fun initCatRetrofit() {
        val okHttpClient = OkHttpClient.Builder()
            .build()
        val retrofit =
            Retrofit.Builder().baseUrl("https://thatcopy.pw/catapi/rest/").client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build()
        catApi = retrofit.create(CatApi::class.java)
    }


    fun getDuckPicture(){

        duckApi.getDuck().enqueue(object : Callback<DuckResponse> {
            override fun onResponse(
                call: Call<DuckResponse>,
                response: Response<DuckResponse>
            ) {
                Log.e("response", "success")
                Log.e("response", response.body().toString())
                response.body()?.url.toString()
            }

            override fun onFailure(call: Call<DuckResponse>, t: Throwable) {
                Log.e("response", "failure", t)
            }
        })

    }

    fun getCatPicture() {
        catApi.getCat().enqueue(object : Callback<CatResponse> {
            override fun onResponse(
                call: Call<CatResponse>,
                response: Response<CatResponse>
            ) {
                Log.e("response", "success")
                Log.e("response", response.body().toString())
                responseCat = (response.body()?.url.toString())

            }
            override fun onFailure(call: Call<CatResponse>, t: Throwable) {
                Log.e("response", "failure", t)
                responseCat = ("")
            }
        })
    }
}