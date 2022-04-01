package com.softsquared.template.kotlin.src.main.login

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.login.models.LoginResponse
import com.softsquared.template.kotlin.src.main.login.models.PostLoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivityService(val view: LoginActivityView) {
    fun tryPostLogin(email: String, password: String) {
        val loginRetrofitInstance =
            ApplicationClass.sRetrofit.create(LoginRetrofitInstance::class.java)
        loginRetrofitInstance.postLoginRequest(PostLoginRequest(email, password))
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.body()?.isSuccess == true) {
                        view.postLoginSuccess(response.body() as LoginResponse)
                    } else {
                        view.postLoginFailure(response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    view.postLoginFailure(t.message ?: "로그인 실패 오류")
                }
            })
    }
}