package com.softsquared.template.kotlin.src.main.location

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.home.models.rising.address.HomeAddressRetrofitInstance
import com.softsquared.template.kotlin.src.main.home.models.rising.address.models.HomeAddressResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationService(val view: LocationView) {
    fun tryGetLocation() {
        val homeAddressRetrofitInstance =
            ApplicationClass.sRetrofit.create(HomeAddressRetrofitInstance::class.java)
        homeAddressRetrofitInstance.getAddress().enqueue(object : Callback<HomeAddressResponse> {
            override fun onResponse(
                call: Call<HomeAddressResponse>,
                response: Response<HomeAddressResponse>
            ) {
                if (response.body()?.isSuccess == true) {
                    view.onGetLocationSuccess(response.body() as HomeAddressResponse)
                } else {
                    view.onGetLocationFailure(response.body()?.message!!)
                }

            }

            override fun onFailure(call: Call<HomeAddressResponse>, t: Throwable) {
                view.onGetLocationFailure(t.message ?: "주소 실패")
            }

        })
    }
}