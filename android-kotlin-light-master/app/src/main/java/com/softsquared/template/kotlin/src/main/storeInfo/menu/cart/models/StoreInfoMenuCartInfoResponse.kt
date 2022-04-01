package com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models

import java.io.Serializable

data class StoreInfoMenuCartInfoResponse(
    val menuName: String,
    val menuOptions: String,
    val mulPrice: Int,
    val orderCount: Int,
    val cartIdx: Int
):Serializable