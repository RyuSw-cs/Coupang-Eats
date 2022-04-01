package com.softsquared.template.kotlin.src.main.myPage

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.myPage.models.MyEatsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageService(val view: MyPageView) {
    fun tryGetMyEats() {
        val myPageRetrofitInstance =
            ApplicationClass.sRetrofit.create(MyPageRetrofitInstance::class.java)
        myPageRetrofitInstance.getMyEats().enqueue(object : Callback<MyEatsResponse> {
            override fun onResponse(
                call: Call<MyEatsResponse>,
                response: Response<MyEatsResponse>
            ) {
                if (response.body()?.isSuccess == true) {
                    view.onGetMyEatsSuccess(response.body() as MyEatsResponse)
                } else {
                    view.onGetMyEatsFailure(response.body()?.message!!)
                }
            }

            override fun onFailure(call: Call<MyEatsResponse>, t: Throwable) {
                view.onGetMyEatsFailure(t.message ?: "마이이츠 에러")
            }
        })
    }
}