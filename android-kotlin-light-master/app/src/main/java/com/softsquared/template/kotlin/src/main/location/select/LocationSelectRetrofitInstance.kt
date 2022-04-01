package com.softsquared.template.kotlin.src.main.location.select

import com.softsquared.template.kotlin.src.main.location.add.models.LocationModifyResponse
import retrofit2.Call
import retrofit2.http.PUT
import retrofit2.http.Query

interface LocationSelectRetrofitInstance {
    @PUT("users/address/choice")
    fun putAddress(
        @Query("addressIdx") addressIdx: Int
    ): Call<LocationModifyResponse>
}