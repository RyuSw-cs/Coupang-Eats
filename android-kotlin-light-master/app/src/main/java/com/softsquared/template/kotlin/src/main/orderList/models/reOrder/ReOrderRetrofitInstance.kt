package com.softsquared.template.kotlin.src.main.orderList.models.reOrder

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface ReOrderRetrofitInstance {
    @POST("orders/delivery/reorder")
    fun postReOrder(
        @Query("userOrderIdx") userOrderIdx: Int
    ): Call<ReOrderResponse>

    @POST("orders/delivery/reorder/new")
    fun newPostReOrder(
        @Query("userOrderIdx") userOrderIdx: Int
    ): Call<ReOrderResponse>
}

