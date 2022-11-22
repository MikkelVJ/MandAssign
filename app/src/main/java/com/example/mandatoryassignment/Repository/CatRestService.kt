package com.example.mandatoryassignment.Repository

import com.example.mandatoryassignment.Models.Cat
import retrofit2.Call
import retrofit2.http.*

interface CatRestService {
    @GET("cats")
    fun getAllCats(): Call<List<Cat>>

    @POST("cats")
    fun saveCat(@Body cat: Cat): Call<Cat>

    @DELETE("cats/{id}")
    fun deleteCat(@Path("id") id: Int): Call<Cat>
}