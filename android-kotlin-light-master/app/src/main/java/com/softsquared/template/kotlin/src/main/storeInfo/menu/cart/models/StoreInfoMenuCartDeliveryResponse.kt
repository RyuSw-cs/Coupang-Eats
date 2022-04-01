package com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models

import java.io.Serializable

data class StoreInfoMenuCartDeliveryResponse(
    val minPrice: Int,
    val maxPrice: Int,
    val deliveryFee: Int
):Serializable