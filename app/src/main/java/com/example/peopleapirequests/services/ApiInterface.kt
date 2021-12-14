package com.example.peopleapirequests.services

import android.app.Person
import com.example.peopleapirequests.model.PersonHolder
import com.example.peopleapirequests.model.PersonModel
import com.example.peopleapirequests.model.PersonModelItem
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("test/")
    fun getApiData(): Call<PersonModel>

    @POST("test/")
    fun postApiData(@Body person: PersonHolder): Call<PersonHolder>

    @PUT("test/{id}")
    fun updateApiData(@Path("id") id: Int, @Body person: PersonModelItem): Call<PersonModelItem>

    @DELETE("test/{id}")
    fun deleteApiData(@Path("id") id: Int): Call<Void>
}