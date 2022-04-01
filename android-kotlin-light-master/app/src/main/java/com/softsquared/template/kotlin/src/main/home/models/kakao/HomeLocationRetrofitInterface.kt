package com.softsquared.template.kotlin.src.main.home.models.kakao

import com.softsquared.template.kotlin.src.main.home.models.kakao.models.HomeLocationResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface HomeLocationRetrofitInterface {
    @GET("v2/local/geo/coord2address.json")
    fun getMyLocation(
        @Header("Authorization") token: String,
        @Query("x") longitude: String,
        @Query("y") latitude: String
    ): Call<HomeLocationResultResponse>
}