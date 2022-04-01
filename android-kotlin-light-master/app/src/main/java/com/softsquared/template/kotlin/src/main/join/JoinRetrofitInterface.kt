package com.softsquared.template.kotlin.src.main.join

import com.softsquared.template.kotlin.src.main.join.models.JoinResponse
import com.softsquared.template.kotlin.src.main.join.models.PostJoinRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface JoinRetrofitInterface {
    @POST("users/sign-up")
    fun postSignUp(
        @Body joinData: PostJoinRequest
    ): Call<JoinResponse>
}