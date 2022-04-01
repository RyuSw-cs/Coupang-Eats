package com.softsquared.template.kotlin.src.main.home.models.kakao

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.home.models.kakao.models.HomeLocationResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRoadAddressService(val view: HomeRoadAddressView) {
    //매개변수로 데이터를 받아야함.
    fun tryGetLocation(long: String, lat: String) {
        val locationRetrofitInterface =
            ApplicationClass.kRetrofit.create(HomeLocationRetrofitInterface::class.java)
        locationRetrofitInterface.getMyLocation("KakaoAK ${ApplicationClass.KAKAO_REST_API_KEY}", long, lat)
            .enqueue(object : Callback<HomeLocationResultResponse> {
                override fun onResponse(
                    call: Call<HomeLocationResultResponse>,
                    responseHomeResult: Response<HomeLocationResultResponse>
                ) {
                    if (responseHomeResult.isSuccessful) {
                        view.onGetLocationSuccess(responseHomeResult.body() as HomeLocationResultResponse)
                    } else {
                        view.onGetLocationFailure(responseHomeResult.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<HomeLocationResultResponse>, t: Throwable) {
                    view.onGetLocationFailure(t.message ?: "위치 정보 실패")
                }
            })
    }
}