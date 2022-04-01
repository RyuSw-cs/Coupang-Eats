package com.softsquared.template.kotlin.src.main.orderList.models.reOrder

import com.softsquared.template.kotlin.config.ApplicationClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReOrderService(val view: ReOrderView) {
    fun tryPostReOrder(userOrderIdx: Int) {
        val reOrderRetrofitInstance =
            ApplicationClass.sRetrofit.create(ReOrderRetrofitInstance::class.java)
        reOrderRetrofitInstance.postReOrder(userOrderIdx)
            .enqueue(object : Callback<ReOrderResponse> {
                override fun onResponse(
                    call: Call<ReOrderResponse>,
                    response: Response<ReOrderResponse>
                ) {
                    if (response.body()?.isSuccess == true) {
                        view.onPostReOrderSuccess(response.body() as ReOrderResponse)
                    } else {
                        view.onPostReOrderFailure(response.body()?.message!!)
                    }
                }

                override fun onFailure(call: Call<ReOrderResponse>, t: Throwable) {
                    view.onPostReOrderFailure(t.message ?: "재주문 실패")
                }
            })
    }

    fun tryNewPostReOrder(userOrderIdx: Int) {
        val reOrderRetrofitInstance =
            ApplicationClass.sRetrofit.create(ReOrderRetrofitInstance::class.java)
        reOrderRetrofitInstance.newPostReOrder(userOrderIdx)
            .enqueue(object : Callback<ReOrderResponse> {
                override fun onResponse(
                    call: Call<ReOrderResponse>,
                    response: Response<ReOrderResponse>
                ) {
                    if (response.body()?.isSuccess == true) {
                        view.onNewPostReOrderSuccess(response.body() as ReOrderResponse)
                    } else {
                        view.onNewPostReOrderFailure(response.body()?.message!!)
                    }
                }

                override fun onFailure(call: Call<ReOrderResponse>, t: Throwable) {
                    view.onNewPostReOrderFailure(t.message ?: "재주문 실패")
                }
            })
    }
}