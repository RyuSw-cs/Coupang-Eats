package com.softsquared.template.kotlin.src.main.order

import com.softsquared.template.kotlin.src.main.order.models.OrderDeliveryResponse
import com.softsquared.template.kotlin.src.main.order.models.OrderModifyResponse
import com.softsquared.template.kotlin.src.main.order.models.PostOrderRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface OrderRetrofitInstance {
    @POST("orders/delivery")
    fun postOrder(
        @Body body: PostOrderRequest,
        @Query("cartList") cartList: List<String>
    ): Call<OrderDeliveryResponse>

    @PUT("orders/cart")
    fun putOrder(
        @Body changeCount: Int,
        @Query("storeIdx") storeIdx: Int,
        @Query("cartIdx") cartIdx: Int
    ): Call<OrderModifyResponse>
}