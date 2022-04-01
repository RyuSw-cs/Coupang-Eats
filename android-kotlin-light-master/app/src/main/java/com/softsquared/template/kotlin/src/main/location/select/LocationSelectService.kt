package com.softsquared.template.kotlin.src.main.location.select

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.location.add.LocationAddRetrofitInterface
import com.softsquared.template.kotlin.src.main.location.add.models.LocationModifyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationSelectService(val listView: LocationSelectListView) {
    fun tryPutCurrentAddress(
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
                if (response.body()?.isSuccess == true) {
                    listView.onPutLocationSuccess(response.body() as LocationModifyResponse)
                } else {
                    listView.onPutLocationFailure(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<LocationModifyResponse>, t: Throwable) {
                listView.onPutLocationFailure(t.message ?: "주소 전송 실패")
            }
        })
    }
}