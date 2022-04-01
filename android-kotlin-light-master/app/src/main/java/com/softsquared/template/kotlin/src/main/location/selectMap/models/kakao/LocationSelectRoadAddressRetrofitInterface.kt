package com.softsquared.template.kotlin.src.main.location.selectMap.models.kakao

import com.softsquared.template.kotlin.src.main.location.selectMap.models.kakao.models.LocationSelectResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface LocationSelectRoadAddressRetrofitInterface {
    @GET("v2/local/geo/coord2address.json")
    fun getMyLocation(
        @Header("Authorization") token: String,
        @Query("x") longitude: String,
        @Query("y") latitude: String
    ): Call<LocationSelectResultResponse>
}