package com.softsquared.template.kotlin.src.main.order

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.order.models.OrderDeliveryResponse
import com.softsquared.template.kotlin.src.main.order.models.OrderModifyResponse
import com.softsquared.template.kotlin.src.main.order.models.PostOrderRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderActivityService(val view: OrderActivityView) {
    fun tryPostOrder(
        userAddressIdx: Int,
        storeIdx: Int,
        userCouponIdx: Int,
        message: String,
        isSpoon: String,
        deliveryManOptionIdx: Int,
        deliveryManContent: String,
        cartList: List<String>
    ) {
        val orderRetrofitInstance =
            ApplicationClass.sRetrofit.create(OrderRetrofitInstance::class.java)
        orderRetrofitInstance.postOrder(
            PostOrderRequest(
                userAddressIdx,
                storeIdx,
                userCouponIdx,
                message,
                isSpoon,
                deliveryManOptionIdx,
                deliveryManContent
            ), cartList
        ).enqueue(object : Callback<OrderDeliveryResponse> {
            override fun onResponse(
                call: Call<OrderDeliveryResponse>,
                response: Response<OrderDeliveryResponse>
            ) {
                if (response.body()?.isSuccess == true) {
                    view.onPostOrderSuccess(response.body() as OrderDeliveryResponse)
                } else {
                    view.onPostOrderFailure(response.body()?.message!!)
                }
            }

            override fun onFailure(call: Call<OrderDeliveryResponse>, t: Throwable) {
                view.onPostOrderFailure(t.message ?: "주문 실패")
            }
        })
    }

    fun tryPutOrder(changeCount: Int, storeIdx: Int, cartIdx: Int) {
        val orderRetrofitInstance =
            ApplicationClass.sRetrofit.create(OrderRetrofitInstance::class.java)
        orderRetrofitInstance.putOrder(changeCount, storeIdx, cartIdx)
            .enqueue(object : Callback<OrderModifyResponse> {
                override fun onResponse(
                    call: Call<OrderModifyResponse>,
                    response: Response<OrderModifyResponse>
                ) {
                    if (response.body()?.isSuccess == true) {
                        view.onPutOrderSuccess(response.body() as OrderModifyResponse)
                    } else {
                        view.onPostOrderFailure(response.body()?.message!!)
                    }
                }

                override fun onFailure(call: Call<OrderModifyResponse>, t: Throwable) {
                    view.onPutOrderFailure(t.message ?: "주문 수정 실패")
                }
            })
    }
}