package com.softsquared.template.kotlin.src.main.storeInfo.models

data class StoreInfoDeliveryResponse(
    val storeIdx: Int,
    val orderPrice: String,
    val deliveryFee: String
)
