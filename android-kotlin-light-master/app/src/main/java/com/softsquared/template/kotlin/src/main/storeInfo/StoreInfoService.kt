package com.softsquared.template.kotlin.src.main.storeInfo

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.storeInfo.models.StoreInfoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreInfoService(val view: StoreInfoView) {
    fun tryGetStoreInfo(userLong: Double, userLat: Double, storeIdx: Int) {
        val storeInfoRetrofitInstance =
            ApplicationClass.sRetrofit.create(StoreInfoRetrofitInstance::class.java)
        storeInfoRetrofitInstance.getStoreDetailInfo(userLong, userLat, storeIdx)
            .enqueue(object : Callback<StoreInfoResponse> {
                override fun onResponse(
                    call: Call<StoreInfoResponse>,
                    response: Response<StoreInfoResponse>
                ) {
                    if (response.body()?.isSuccess == true) {
                        view.onGetStoreInfoSuccess(response.body() as StoreInfoResponse)
                    } else {
                        view.onGetStoreInfoFailure(response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<StoreInfoResponse>, t: Throwable) {
                    view.onGetStoreInfoFailure(t.message ?: "가게 상세 정보 실패")
                }
            })
    }
}
