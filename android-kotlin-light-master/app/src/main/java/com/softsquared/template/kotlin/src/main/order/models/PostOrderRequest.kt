package com.softsquared.template.kotlin.src.main.order.models

data class PostOrderRequest(
    val userAddressIdx:Int,
    val storeIdx: Int,
    val userCouponIdx: Int,
    val message: String,
    val isSpoon: String,
    val deliveryManOptionIdx: Int,
    val deliveryManContent: String,
    val deliveryFee:Int
)
