package com.softsquared.template.kotlin.src.main.storeInfo.menu

import com.softsquared.template.kotlin.src.main.storeInfo.menu.models.StoreInfoMenuResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StoreInfoMenuRetrofitInstance {
    @GET("stores/options")
    fun getMenu(
        @Query("storeIdx") storeIdx: Int,
        @Query("menuIdx") menuIdx: Int
    ): Call<StoreInfoMenuResponse>
}