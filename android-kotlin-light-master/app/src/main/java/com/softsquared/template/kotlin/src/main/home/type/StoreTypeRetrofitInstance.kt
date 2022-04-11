package com.softsquared.template.kotlin.src.main.home.type

import com.softsquared.template.kotlin.src.main.home.models.rising.store.models.HomeStoreInfoResponse
import com.softsquared.template.kotlin.src.main.home.models.rising.store.models.HomeStoreTypeResponse
import com.softsquared.template.kotlin.src.main.home.type.models.HomeStoreGetTypeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StoreTypeRetrofitInstance {
    @GET("stores/home/type")
    fun getStoreType(
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double,
        @Query("sort") sort: String,
        @Query("isCheetah") isCheetah: String,
        @Query("deliveryFee") deliveryFee: String,
        @Query("minimumPrice") minimumPrice: String,
        @Query("isToGo") isToGo: String,
        @Query("type") type: String
    ): Call<HomeStoreGetTypeResponse>
}