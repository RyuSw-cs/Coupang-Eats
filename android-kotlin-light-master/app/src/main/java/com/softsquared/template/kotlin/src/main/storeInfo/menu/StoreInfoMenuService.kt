package com.softsquared.template.kotlin.src.main.storeInfo.menu

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.storeInfo.menu.models.StoreInfoMenuResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreInfoMenuService(val view: StoreInfoMenuView) {
    fun tryGetMenu(storeIdx: Int, menuIdx: Int) {
        val storeInfoMenuRetrofitInstance =
            ApplicationClass.sRetrofit.create(StoreInfoMenuRetrofitInstance::class.java)
        storeInfoMenuRetrofitInstance.getMenu(storeIdx, menuIdx)
            .enqueue(object : Callback<StoreInfoMenuResponse> {
                override fun onResponse(
                    call: Call<StoreInfoMenuResponse>,
                    response: Response<StoreInfoMenuResponse>
                ) {
                    if (response.body()?.isSuccess == true) {
                        view.onGetStoreInfoMenuSuccess(response.body() as StoreInfoMenuResponse)
                    } else {
                        view.onGetStoreInfoMenuFailure(response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<StoreInfoMenuResponse>, t: Throwable) {
                    view.onGetStoreInfoMenuFailure(t.message ?: "메뉴 상세 실패")
                }
            })
    }
}