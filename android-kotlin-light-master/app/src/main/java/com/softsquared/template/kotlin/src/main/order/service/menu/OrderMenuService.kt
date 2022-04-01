package com.softsquared.template.kotlin.src.main.order.service.menu

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.storeInfo.StoreInfoRetrofitInstance
import com.softsquared.template.kotlin.src.main.storeInfo.models.StoreInfoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderMenuService(val view: OrderMenuView) {
    fun tryGetMenu(long: Double, lat: Double, storeIdx: Int) {
        val storeInfoMenuCartRetrofitInterface =
            ApplicationClass.sRetrofit.create(StoreInfoRetrofitInstance::class.java)
        storeInfoMenuCartRetrofitInterface.getStoreDetailInfo(long, lat, storeIdx).enqueue(object :
            Callback<StoreInfoResponse> {
            override fun onResponse(
                call: Call<StoreInfoResponse>,
                response: Response<StoreInfoResponse>
            ) {
                if (response.body()?.isSuccess == true) {
                    view.onGetOrderMenuSuccess(response.body() as StoreInfoResponse)
                } else {
                    view.onGetOrderMenuFailure(response.body()?.message!!)
                }
            }

            override fun onFailure(call: Call<StoreInfoResponse>, t: Throwable) {
                view.onGetOrderMenuFailure(t.message ?: "메뉴 정보 실패")
            }
        })
    }
}