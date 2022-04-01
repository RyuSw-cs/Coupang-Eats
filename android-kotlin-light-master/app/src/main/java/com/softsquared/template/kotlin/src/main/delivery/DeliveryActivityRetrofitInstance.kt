package com.softsquared.template.kotlin.src.main.delivery

import com.softsquared.template.kotlin.src.main.delivery.models.DeliveryCancelResponse
import retrofit2.Call
import retrofit2.http.PUT
import retrofit2.http.Query

interface DeliveryActivityRetrofitInstance {
    @PUT("orders/delivery/status")
    fun deleteOrderList(
        @Query("userOrderIdx") userOrderIdx: Int
    ): Call<DeliveryCancelResponse>
}