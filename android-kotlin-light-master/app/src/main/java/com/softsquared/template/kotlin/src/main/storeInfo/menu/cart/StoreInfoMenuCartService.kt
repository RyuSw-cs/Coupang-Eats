package com.softsquared.template.kotlin.src.main.storeInfo.menu.cart

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.RequestStoreMenuInfoCartBody
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartGetResponse
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartPostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreInfoMenuCartService(val view: StoreInfoMenuCartView) {
    fun tryPostCart(
        storeIdx: Int,
        menuIdx: Int,
        menuOptions: String,
        orderCount: Int,
        orderPrice: Int
    ) {
        val storeInfoMenuCartRetrofitInterface =
            ApplicationClass.sRetrofit.create(StoreInfoMenuCartRetrofitInterface::class.java)
        storeInfoMenuCartRetrofitInterface.postCart(
            storeIdx, menuIdx,
            RequestStoreMenuInfoCartBody(
                menuOptions,
                orderCount,
                orderPrice
            )
        ).enqueue(
            object : Callback<StoreInfoMenuCartPostResponse> {
                override fun onResponse(
                    call: Call<StoreInfoMenuCartPostResponse>,
                    postResponse: Response<StoreInfoMenuCartPostResponse>
                ) {
                    if (postResponse.body()?.isSuccess == true) {
                        view.onPostStoreInfoMenuCartSuccess(postResponse.body() as StoreInfoMenuCartPostResponse)
                    } else {
                        view.onPostStoreInfoMenuCartFailure(postResponse.body()?.message!!)
                    }
                }

                override fun onFailure(call: Call<StoreInfoMenuCartPostResponse>, t: Throwable) {
                    view.onPostStoreInfoMenuCartFailure(t.message ?: "카트 담기 실패")
                }
            }
        )
    }

    fun tryPostNewCart(
        storeIdx: Int,
        menuIdx: Int,
        menuOptions: String,
        orderCount: Int,
        orderPrice: Int
    ) {
        val storeInfoMenuCartRetrofitInterface =
            ApplicationClass.sRetrofit.create(StoreInfoMenuCartRetrofitInterface::class.java)
        storeInfoMenuCartRetrofitInterface.postNewCart(
            storeIdx, menuIdx,
            RequestStoreMenuInfoCartBody(
                menuOptions,
                orderCount,
                orderPrice
            )
        ).enqueue(
            object : Callback<StoreInfoMenuCartPostResponse> {
                override fun onResponse(
                    call: Call<StoreInfoMenuCartPostResponse>,
                    postResponse: Response<StoreInfoMenuCartPostResponse>
                ) {
                    if (postResponse.body()?.isSuccess == true) {
                        view.onPostStoreInfoMenuNewCartSuccess(postResponse.body() as StoreInfoMenuCartPostResponse)
                    } else {
                        view.onPostStoreInfoMenuNewCartFailure(postResponse.body()?.message!!)
                    }
                }

                override fun onFailure(call: Call<StoreInfoMenuCartPostResponse>, t: Throwable) {
                    view.onPostStoreInfoMenuNewCartFailure(t.message ?: "카트 담기 실패")
                }
            }
        )
    }

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