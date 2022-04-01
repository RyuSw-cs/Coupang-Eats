package com.softsquared.template.kotlin.src.main.join

import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.join.models.JoinResponse
import com.softsquared.template.kotlin.src.main.join.models.PostJoinRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinService(val view:JoinActivityView) {
    fun tryPostJoin(email: String, password: String, name: String, phoneNumber: String) {
        val joinRetrofitInterface =
            ApplicationClass.sRetrofit.create(JoinRetrofitInterface::class.java)
        joinRetrofitInterface.postSignUp(
            PostJoinRequest(
                email = email,
                password = password,
                userName = name,
                phoneNumber = phoneNumber
            )
        ).enqueue(object : Callback<JoinResponse> {
            override fun onResponse(call: Call<JoinResponse>, response: Response<JoinResponse>) {
                if (response.isSuccessful){
                    if(response.body()?.isSuccess == true){
                        view.onPostJoinSuccess(response.body() as JoinResponse)
                    }
                    else{
                        view.onPostJoinFailure(response.body()?.message!!)
                    }
                }
                else{
                   view.onPostJoinFailure(response.errorBody().toString())
                }

            }

            override fun onFailure(call: Call<JoinResponse>, t: Throwable) {
                view.onPostJoinFailure(t.message ?: "회원가입 실패")
            }
        })
    }
}