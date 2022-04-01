package com.softsquared.template.kotlin.src.main.location.add

import com.softsquared.template.kotlin.src.main.location.add.models.LocationAddResponse
import com.softsquared.template.kotlin.src.main.location.add.models.LocationModifyResponse
import com.softsquared.template.kotlin.src.main.location.add.models.PostLocationRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface LocationAddRetrofitInterface {
    @POST("users/address")
    fun postAddress(
        @Body body: PostLocationRequest
    ): Call<LocationAddResponse>

    @PUT("users/address/choice")
    fun putCurrentAddress(
        @Query("addressIdx") addressIdx: Int
    ): Call<LocationModifyResponse>
}