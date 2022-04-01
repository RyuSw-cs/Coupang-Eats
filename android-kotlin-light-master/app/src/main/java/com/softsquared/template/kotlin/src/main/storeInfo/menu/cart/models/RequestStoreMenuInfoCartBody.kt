package com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models

data class RequestStoreMenuInfoCartBody(
    val menuOptions: String,
    val orderCount: Int,
    val orderPrice: Int
)
