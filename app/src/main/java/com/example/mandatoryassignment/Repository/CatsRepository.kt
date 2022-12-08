package com.example.mandatoryassignment.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mandatoryassignment.Models.Cat
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class CatsRepository {
    private val url = "https://anbo-restlostcats.azurewebsites.net/api/"

    private val restService: CatRestService
    val catsLiveData: MutableLiveData<List<Cat>> = MutableLiveData<List<Cat>>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val updateMessageLiveData: MutableLiveData<String> = MutableLiveData()

    init {

        val build: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        restService = build.create(CatRestService::class.java)
        getCats()
    }

    fun getCats() {
        restService.getAllCats().enqueue(object : Callback<List<Cat>> {
            override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
                if (response.isSuccessful) {
                    val b: List<Cat>? = response.body()
                    Log.d("PINEAPPLE", b.toString())
                    catsLiveData.postValue(b!!)
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("ORANGE", message)
                }
            }

            override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }

    fun add(cat: Cat) {
        restService.saveCat(cat).enqueue(object : Callback<Cat> {
            override fun onResponse(call: Call<Cat>, response: Response<Cat>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Added: " + response.body())
                    updateMessageLiveData.postValue("Added: " + response.body())
                    getCats()
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<Cat>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("Apple", t.message!!)
            }
        })
    }

    fun delete(id: Int) {
        restService.deleteCat(id).enqueue(object : Callback<Cat> {
            override fun onResponse(call: Call<Cat>, response: Response<Cat>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Updated: " + response.body())
                    updateMessageLiveData.postValue("Deleted: " + response.message())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<Cat>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }

    fun sortByName() {
        catsLiveData.value = catsLiveData.value?.sortedBy { it.name }
    }

    fun sortByNameDescending() {
        catsLiveData.value = catsLiveData.value?.sortedByDescending { it.name }
    }

    fun sortByDate() {
        catsLiveData.value = catsLiveData.value?.sortedBy { it.date }
    }

    fun sortByDateDescending() {
        catsLiveData.value = catsLiveData.value?.sortedByDescending { it.date }
    }

    fun filterByName(name: String) {
        if (name.isBlank()) {
            getCats()
        } else {
            val filter: List<Cat>? = catsLiveData.value?.filter { cat -> cat.name.contains(name) }
            Log.d("KIWI", filter.toString())
            catsLiveData.value = filter!!
        }
    }
}