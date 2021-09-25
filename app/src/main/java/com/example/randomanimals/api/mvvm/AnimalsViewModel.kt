package com.example.randomanimals.api.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class AnimalsViewModel : ViewModel() {

    val duckLive = MutableLiveData<String>()
    val catLive = MutableLiveData<String>()

    init {
        Log.e("check", "created")

    }

    fun saveDuck(url: String) {
        duckLive.postValue(url)
        Log.e("duck",duckLive.value.toString())
    }

    fun saveCat(url: String) {
        catLive.value = url
    }

}