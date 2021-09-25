package com.example.randomanimals.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.randomanimals.R
import com.example.randomanimals.api.CatApi
import com.example.randomanimals.api.CatResponse
import com.example.randomanimals.api.DuckApi
import com.example.randomanimals.api.DuckResponse
import com.example.randomanimals.api.mvvm.AnimalsViewModel
import com.example.randomanimals.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var duckApi: DuckApi
    private lateinit var catApi: CatApi
    private lateinit var binding: ActivityMainBinding
    private lateinit var animalVM: AnimalsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()

    }

    private fun init() {
        initCatRetrofit()
        initDuckRetrofit()
        initViewModels()
        initListeners()
    }


    private fun initViewModels() {
        animalVM = ViewModelProvider(this)[AnimalsViewModel::class.java]
        /*check of ViewModel's changes */
        animalVM.catLive.observe(this, { url ->
            setPicture(url)
        })
        animalVM.duckLive.observe(this, { url ->
            setPicture(url)
        })
    }

    /*when loading picture - use loading screen*/
    private fun initListeners() {

        binding.buttonCat.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                getCatPicture()
            }
            Glide.with(this@MainActivity).load(R.drawable.load).into(binding.imageAnimal)

        }
        binding.buttonDuck.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                getDuckPicture()
            }
            Glide.with(this@MainActivity).load(R.drawable.load).into(binding.imageAnimal)
        }
    }


    private fun initDuckRetrofit() {
        val okHttpClient = OkHttpClient.Builder()
            .build()
        val retrofit = Retrofit.Builder().baseUrl("https://random-d.uk/api/").client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
        duckApi = retrofit.create(DuckApi::class.java)
    }

    private fun initCatRetrofit() {
        val okHttpClient = OkHttpClient.Builder()
            .build()
        val retrofit =
            Retrofit.Builder().baseUrl("https://thatcopy.pw/catapi/rest/").client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build()
        catApi = retrofit.create(CatApi::class.java)
    }



    private fun getDuckPicture() {
        duckApi.getDuck().enqueue(object : Callback<DuckResponse> {
            override fun onResponse(
                call: Call<DuckResponse>,
                response: Response<DuckResponse>
            ) {
                Log.e("response", "success")
                Log.e("response", response.body().toString())

                setPicture(response.body()?.url.toString())
                /* calling ViewModel*/
                animalVM.saveDuck(response.body()?.url.toString())
            }

            override fun onFailure(call: Call<DuckResponse>, t: Throwable) {
                Log.e("response", "failure", t)
            }
        })

    }

    private fun getCatPicture() {
        catApi.getCat().enqueue(object : Callback<CatResponse> {
            override fun onResponse(
                call: Call<CatResponse>,
                response: Response<CatResponse>
            ) {
                Log.e("response", "success")
                Log.e("response", response.body().toString())
                setPicture(response.body()?.url.toString())
                /* calling ViewModel*/
                animalVM.saveCat(response.body()?.url.toString())
            }

            override fun onFailure(call: Call<CatResponse>, t: Throwable) {
                Log.e("response", "failure", t)
            }
        })
    }

    /* set picture on ImageView with glide*/
    private fun setPicture(url: String?) {
        with(binding) {
            if (url != null) {
                Glide.with(this@MainActivity).load(url).into(imageAnimal)
                Log.e("GLIDE", url)
            } else Toast.makeText(this@MainActivity, "no url", Toast.LENGTH_SHORT).show()
        }
        binding.imageAnimal.layoutParams.width = 1000
        binding.imageAnimal.layoutParams.height = 1000
    }
}