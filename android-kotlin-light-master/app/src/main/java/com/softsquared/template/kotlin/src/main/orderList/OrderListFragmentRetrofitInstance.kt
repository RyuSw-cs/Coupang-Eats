package com.softsquared.template.kotlin.src.main.orderList

import com.softsquared.template.kotlin.src.main.orderList.models.OrderListResponse
import retrofit2.Call
import retrofit2.http.GET

interface OrderListFragmentRetrofitInstance {
    @GET("orders/delivery-list")
    fun getOrderList(

    ): Call<OrderListResponse>
}