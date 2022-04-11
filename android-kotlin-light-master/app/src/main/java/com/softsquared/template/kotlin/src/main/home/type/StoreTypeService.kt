package com.softsquared.template.kotlin.src.main.home.type

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.home.models.rising.store.models.HomeStoreTypeResponse
import com.softsquared.template.kotlin.src.main.home.type.models.HomeStoreGetTypeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreTypeService(val view: StoreTypeView) {
    fun tryGetStoreType(
        longitude: Double,
        latitude: Double,
        sort: String,
        isCheetah: String,
        deliveryFee: String,
        minimumPrice: String,
        isToGo: String,
        type: String
    ) {
        val storeTypeRetrofitInstance =
            ApplicationClass.sRetrofit.create(StoreTypeRetrofitInstance::class.java)
        storeTypeRetrofitInstance.getStoreType(
            longitude,
            latitude,
            sort,
            isCheetah,
            deliveryFee,
            minimumPrice,
            isToGo,
            type
        ).enqueue(object :
            Callback<HomeStoreGetTypeResponse> {
            override fun onResponse(
                call: Call<HomeStoreGetTypeResponse>,
                response: Response<HomeStoreGetTypeResponse>
            ) {
                if(response.body()?.isSuccess == true){
                    view.onGetStoreTypeInfoSuccess(response.body() as HomeStoreGetTypeResponse)
                }
                else{
                    view.onGetStoreTypeInfoFailure(response.body()?.message!!)
                }
            }

            override fun onFailure(call: Call<HomeStoreGetTypeResponse>, t: Throwable) {
                view.onGetStoreTypeInfoFailure(t.message ?: "종류별 가게 데이터 실패")
            }
        })
    }
}