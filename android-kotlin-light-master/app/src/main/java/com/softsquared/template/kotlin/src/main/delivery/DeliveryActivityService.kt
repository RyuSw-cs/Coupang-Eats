package com.softsquared.template.kotlin.src.main.delivery

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.delivery.models.DeliveryCancelResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeliveryActivityService(val view: DeliveryActivityView) {
    fun tryDeleteDelivery(userOrderIdx: Int) {
        val deliveryActivityRetrofitInstance =
            ApplicationClass.sRetrofit.create(DeliveryActivityRetrofitInstance::class.java)
        deliveryActivityRetrofitInstance.deleteOrderList(userOrderIdx)
            .enqueue(object : Callback<DeliveryCancelResponse> {
                override fun onResponse(
                    call: Call<DeliveryCancelResponse>,
                    response: Response<DeliveryCancelResponse>
                ) {
                    if (response.body()?.isSuccess == true) {
                        view.onDeleteDeliveryCancelSuccess(response.body() as DeliveryCancelResponse)
                    } else {
                        view.onDeleteDeliveryCancelFailure(response.body()?.message!!)
                    }
                }

                override fun onFailure(call: Call<DeliveryCancelResponse>, t: Throwable) {
                    view.onDeleteDeliveryCancelFailure(t.message ?: "주문 삭제 실패")
                }

            })
    }
}