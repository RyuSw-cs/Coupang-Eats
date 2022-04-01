package com.softsquared.template.kotlin.src.main.order.service.cart

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.order.service.cart.models.OrderCartDeleteResponse
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.StoreInfoMenuCartRetrofitInterface
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.RequestStoreMenuInfoCartBody
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartGetResponse
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartPostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderCartService(val view: OrderCartView) {
    fun tryGetCart() {
        val cartRetrofitInterface =
            ApplicationClass.sRetrofit.create(StoreInfoMenuCartRetrofitInterface::class.java)
        cartRetrofitInterface.getCart().enqueue(object : Callback<StoreInfoMenuCartGetResponse> {
            override fun onResponse(
                call: Call<StoreInfoMenuCartGetResponse>,
                response: Response<StoreInfoMenuCartGetResponse>
            ) {
                if (response.body()?.isSuccess == true) {
                    view.onGetOrderCartSuccess(response.body() as StoreInfoMenuCartGetResponse)
                } else {
                    view.onGetOrderCartFailure(response.body()?.message!!)
                }
            }

            override fun onFailure(call: Call<StoreInfoMenuCartGetResponse>, t: Throwable) {
                view.onGetOrderCartFailure(t.message ?: "카트 정보 실패")
            }
        })
    }

    fun tryPostCartRecommendMenu(
        menuOptions: String,
        orderCount: Int,
        orderPrice: Int,
        storeIdx: Int,
        menuIdx: Int
    ) {
        val storeInfoMenuCartRetrofitInterface =
            ApplicationClass.sRetrofit.create(StoreInfoMenuCartRetrofitInterface::class.java)
        storeInfoMenuCartRetrofitInterface.postCart(
            storeIdx, menuIdx,
            RequestStoreMenuInfoCartBody(menuOptions, orderCount, orderPrice)
        ).enqueue(object : Callback<StoreInfoMenuCartPostResponse> {
            override fun onResponse(
                call: Call<StoreInfoMenuCartPostResponse>,
                response: Response<StoreInfoMenuCartPostResponse>
            ) {
                if (response.body()?.isSuccess == true) {
                    view.onPostOrderMenuSuccess(response.body() as StoreInfoMenuCartPostResponse)
                } else {
                    view.onPostOrderMenuFailure(response.body()?.message!!)
                }
            }

            override fun onFailure(call: Call<StoreInfoMenuCartPostResponse>, t: Throwable) {
                view.onPostOrderMenuFailure(t.message ?: "추천 메뉴 담기 실패")
            }
        })
    }

    fun tryDeleteCart(cartIdx: Int) {
        val storeInfoMenuCartRetrofitInterface =
            ApplicationClass.sRetrofit.create(StoreInfoMenuCartRetrofitInterface::class.java)
        storeInfoMenuCartRetrofitInterface.deleteCart(cartIdx)
            .enqueue(object : Callback<OrderCartDeleteResponse> {
                override fun onResponse(
                    call: Call<OrderCartDeleteResponse>,
                    response: Response<OrderCartDeleteResponse>
                ) {
                    if(response.body()?.isSuccess == true){
                        view.onDeleteOrderMenuSuccess(response.body() as OrderCartDeleteResponse)
                    }
                    else{
                        view.onDeleteOrderMenuFailure(response.body()?.message!!)
                    }
                }

                override fun onFailure(call: Call<OrderCartDeleteResponse>, t: Throwable) {
                    view.onDeleteOrderMenuFailure(t.message ?: "카트 삭제 실패")
                }
            })
    }
}