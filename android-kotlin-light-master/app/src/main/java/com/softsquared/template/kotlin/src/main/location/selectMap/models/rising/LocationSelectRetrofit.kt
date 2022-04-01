package com.softsquared.template.kotlin.src.main.location.selectMap.models.rising

import com.softsquared.template.kotlin.src.main.location.selectMap.models.rising.models.LocationSelectResponse
import com.softsquared.template.kotlin.src.main.location.selectMap.models.rising.models.PostLocationSelectRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface LocationSelectRetrofit {
    @POST("/users/address")
    fun postAddress(
        @Query("otherIdx") otherIdx:Int,
        @Body locationBody : PostLocationSelectRequest
    ): Call<LocationSelectResponse>
}