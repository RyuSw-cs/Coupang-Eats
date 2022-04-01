package com.softsquared.template.kotlin.src.main.home.models.rising.autoLogin

import com.softsquared.template.kotlin.config.ApplicationClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AutoLoginService(val view: AutoLoginView) {
    fun tryAutoLogin(accessToken: String?, refreshToken: String?) {
        val mainAutoLoginRetrofitInstance =
            ApplicationClass.sRetrofit.create(AutoLoginRetrofitInstance::class.java)
        mainAutoLoginRetrofitInstance.getAutoLogin(accessToken, refreshToken)
            .enqueue(object : Callback<AutoLoginResponse> {
                override fun onResponse(
                    call: Call<AutoLoginResponse>,
                    response: Response<AutoLoginResponse>
                ) {
                    if (response.body()?.isSuccess == true) {
                        view.onGetAutoLoginSuccess(response.body() as AutoLoginResponse)
                    }
                    else if (response.body()?.code == 2011 || response.body()?.code == 2014) {
                        view.onGetAutoLoginRefresh(response.body()?.code!!)
                    }
                    else{
                        view.onGetAutoLoginFailure(response.body()?.message!!)
                    }
                }

                override fun onFailure(call: Call<AutoLoginResponse>, t: Throwable) {
                    view.onGetAutoLoginFailure(t.message ?: "자동 로그인 실패")
                }
            })
    }
}