package com.softsquared.template.kotlin.src.main.storeInfo.menu.cart

import com.softsquared.template.kotlin.src.main.order.service.cart.models.OrderCartDeleteResponse
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.RequestStoreMenuInfoCartBody
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartGetResponse
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartPostResponse
import retrofit2.Call
import retrofit2.http.*

interface StoreInfoMenuCartRetrofitInterface {
    @POST("orders/cart")
    fun postCart(
        @Query("storeIdx") storeIdx: Int,
        @Query("menuIdx") menuIdx: Int,
        @Body body: RequestStoreMenuInfoCartBody
    ): Call<StoreInfoMenuCartPostResponse>

    @POST("orders/cart/new")
    fun postNewCart(
        @Query("storeIdx") storeIdx: Int,
        @Query("menuIdx") menuIdx: Int,
        @Body body: RequestStoreMenuInfoCartBody
    ): Call<StoreInfoMenuCartPostResponse>

    @GET("orders/cart-list")
    fun getCart(

    ): Call<StoreInfoMenuCartGetResponse>

    @PATCH("orders/cart/deletion")
    fun deleteCart(
        @Body cartIdx: Int
    ): Call<OrderCartDeleteResponse>
}