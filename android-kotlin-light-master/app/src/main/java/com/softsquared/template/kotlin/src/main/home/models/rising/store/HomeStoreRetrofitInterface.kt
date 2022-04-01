package com.softsquared.template.kotlin.src.main.home.models.rising.store

import com.softsquared.template.kotlin.src.main.home.models.rising.store.models.HomeStoreResponse
import retrofit2.Call
import retrofit2.http.*

interface HomeStoreRetrofitInterface {
    @GET("stores/home")
    fun getStore(
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double,
        @Query("categoryIdx") categoryIdx : Int
    ): Call<HomeStoreResponse>
}
