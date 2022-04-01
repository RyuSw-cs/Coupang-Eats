package com.softsquared.template.kotlin.src.main.location.add

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.location.add.models.LocationAddResponse
import com.softsquared.template.kotlin.src.main.location.add.models.LocationModifyResponse
import com.softsquared.template.kotlin.src.main.location.add.models.PostLocationRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationService(val view: LocationView) {
    fun tryPostAddress(
        buildingName: String,
        address: String,
        addressDetail: String,
        addressGuide: String,
        addressLongitude: Double,
        addressLatitude: Double,
        addressTitle: String,
        addressType: String
    ) {
        val locationAddRetrofitInterface =
            ApplicationClass.sRetrofit.create(LocationAddRetrofitInterface::class.java)
        locationAddRetrofitInterface.postAddress(
            PostLocationRequest(
                buildingName,
                address,
                addressDetail,
                addressGuide,
                addressLongitude,
                addressLatitude,
                addressTitle,
                addressType
            )
        ).enqueue(object : Callback<LocationAddResponse> {
            override fun onResponse(
                call: Call<LocationAddResponse>,
                response: Response<LocationAddResponse>
            ) {
                if (response.body()?.isSuccess == true) {
                    view.onPostLocationSuccess(response.body() as LocationAddResponse)
                } else {
                    view.onPostLocationFailure(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<LocationAddResponse>, t: Throwable) {
                view.onPostLocationFailure(t.message ?: "주소 전송 실패")
            }
        })
    }

    fun tryPutAddress(
        categoryIdx: Int
    ) {
        val locationAddRetrofitInterface =
            ApplicationClass.sRetrofit.create(LocationAddRetrofitInterface::class.java)
        locationAddRetrofitInterface.putCurrentAddress(
            categoryIdx
        ).enqueue(object : Callback<LocationModifyResponse> {
            override fun onResponse(
                call: Call<LocationModifyResponse>,
                response: Response<LocationModifyResponse>
            ) {
                if (response.isSuccessful) {
                    view.onPutLocationSuccess(response.body() as LocationModifyResponse)
                } else {
                    view.onPutLocationFailure(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<LocationModifyResponse>, t: Throwable) {
                view.onPutLocationFailure(t.message ?: "주소 전송 실패")
            }
        })
    }
}