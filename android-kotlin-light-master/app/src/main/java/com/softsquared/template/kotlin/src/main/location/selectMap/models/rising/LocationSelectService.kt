package com.softsquared.template.kotlin.src.main.location.selectMap.models.rising

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.location.selectMap.models.rising.models.LocationSelectResponse
import com.softsquared.template.kotlin.src.main.location.selectMap.models.rising.models.PostLocationSelectRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationSelectService(val view: LocationSelectView) {
    fun tryPostAddress(
        type: Int,
        address: String,
        addressDetail: String,
        addressGuide: String,
        userLongitude: Double,
        userLatitude: Double,
        addressType: String,
        status: String
    ) {
        val locationSelectRetrofit =
            ApplicationClass.sRetrofit.create(LocationSelectRetrofit::class.java)
        locationSelectRetrofit.postAddress(
            type,
            PostLocationSelectRequest(
                address,
                addressDetail,
                addressGuide,
                userLongitude,
                userLatitude,
                addressType,
                status
            )
        ).enqueue(object : Callback<LocationSelectResponse> {
            override fun onResponse(
                call: Call<LocationSelectResponse>,
                response: Response<LocationSelectResponse>
            ) {
                if (response.body()?.isSuccess == true) {
                    view.postLocationSuccess(response.body() as LocationSelectResponse)
                } else {
                    view.postLocationFailure(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<LocationSelectResponse>, t: Throwable) {
                view.postLocationFailure(t.message ?: "위치 선택 에러")
            }
        })
    }
}