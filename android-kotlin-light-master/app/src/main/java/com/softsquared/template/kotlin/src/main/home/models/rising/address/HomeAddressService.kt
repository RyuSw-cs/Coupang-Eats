package com.softsquared.template.kotlin.src.main.home.models.rising.address

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.home.models.rising.address.models.HomeAddressResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeAddressService(val view: HomeAddressView) {
    fun tryGetAddress() {
        val homeAddressRetrofitInstance =
            ApplicationClass.sRetrofit.create(HomeAddressRetrofitInstance::class.java)
        homeAddressRetrofitInstance.getAddress().enqueue(object : Callback<HomeAddressResponse> {
            override fun onResponse(
                call: Call<HomeAddressResponse>,
                response: Response<HomeAddressResponse>
            ) {
                if (response.body()?.isSuccess == true) {
                    view.getAddressSuccess(response.body() as HomeAddressResponse)
                } else {
                    view.getAddressFailure(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<HomeAddressResponse>, t: Throwable) {
                view.getAddressFailure(t.message ?: "지도 데이터 실패")
            }
        })
    }
}