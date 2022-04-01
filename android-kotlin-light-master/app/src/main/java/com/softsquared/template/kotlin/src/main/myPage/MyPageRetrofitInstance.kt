package com.softsquared.template.kotlin.src.main.myPage

import com.softsquared.template.kotlin.src.main.myPage.models.MyEatsResponse
import retrofit2.Call
import retrofit2.http.GET

interface MyPageRetrofitInstance {
    @GET("users/my-eats")
    fun getMyEats(

    ): Call<MyEatsResponse>
}