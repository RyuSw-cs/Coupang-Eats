package com.softsquared.template.kotlin.src.main.home.models.rising.autoLogin

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AutoLoginRetrofitInstance {
    @POST("users/sign-in/refresh")
    fun getAutoLogin(
        @Query("X-ACCESS-TOKEN") accessToken:String?,
        @Query("REFRESH-TOKEN") refreshToken:String?
    ): Call<AutoLoginResponse>
}