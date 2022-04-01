package com.softsquared.template.kotlin.src.main.home.models.rising.cart

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.StoreInfoMenuCartRetrofitInterface
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartGetResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartService(val view:CartView) {
    fun tryGetCart() {
        val storeInfoMenuCartRetrofitInterface =
            ApplicationClass.sRetrofit.create(StoreInfoMenuCartRetrofitInterface::class.java)
        storeInfoMenuCartRetrofitInterface.getCart()
            .enqueue(object : Callback<StoreInfoMenuCartGetResponse> {
                override fun onResponse(
                    call: Call<StoreInfoMenuCartGetResponse>,
                    response: Response<StoreInfoMenuCartGetResponse>
                ) {
                    if (response.body()?.isSuccess == true) {
                        view.onGetStoreInfoMenuCartSuccess(response.body() as StoreInfoMenuCartGetResponse)
                    } else {
                        view.onGetPostStoreInfoMenuCartFailure(
                            response.body()?.message ?: "카트 정보 에러"
                        )
                    }
                }

                override fun onFailure(call: Call<StoreInfoMenuCartGetResponse>, t: Throwable) {
                    view.onGetPostStoreInfoMenuCartFailure(t.message ?: "카트 정보 에러")
                }
            })
    }
}