package com.softsquared.template.kotlin.src.main.location.selectMap.models.kakao

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.location.selectMap.models.kakao.models.LocationSelectResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationSelectRoadAddressService(val view: LocationSelectRoadAddressView) {
    //매개변수로 데이터를 받아야함.
    fun tryGetLocation(long: String, lat: String) {
        val locationRetrofitInterface =
            ApplicationClass.kRetrofit.create(LocationSelectRoadAddressRetrofitInterface::class.java)
        locationRetrofitInterface.getMyLocation(
            "KakaoAK ${ApplicationClass.KAKAO_REST_API_KEY}",
            long,
            lat
        )
            .enqueue(object : Callback<LocationSelectResultResponse> {
                override fun onResponse(
                    call: Call<LocationSelectResultResponse>,
                    responseHomeResult: Response<LocationSelectResultResponse>
                ) {
                    if (responseHomeResult.isSuccessful) {
                        if (responseHomeResult.body()?.documents?.get(0)?.locationSelectRoadAddress == null) {
                            view.onGetLocationSuccessWithoutRoadAddress(
                                responseHomeResult.body()?.documents?.get(
                                    0
                                )?.locationSelectAddress!!
                            )
                        } else {
                            view.onGetLocationSuccess(responseHomeResult.body() as LocationSelectResultResponse)
                        }
                    } else {
                        view.onGetLocationFailure(responseHomeResult.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<LocationSelectResultResponse>, t: Throwable) {
                    view.onGetLocationFailure(t.message ?: "위치 정보 실패")
                }
            })
    }
}