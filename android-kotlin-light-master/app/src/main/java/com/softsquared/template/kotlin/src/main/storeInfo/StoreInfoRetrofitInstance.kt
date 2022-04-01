package com.softsquared.template.kotlin.src.main.storeInfo

import com.softsquared.template.kotlin.src.main.storeInfo.models.StoreInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StoreInfoRetrofitInstance {
    @GET("stores/detail")
    fun getStoreDetailInfo(
        //사용자 위도, 경도, idx
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double,
        @Query("storeIdx") storeIdx: Int
    ): Call<StoreInfoResponse>
}