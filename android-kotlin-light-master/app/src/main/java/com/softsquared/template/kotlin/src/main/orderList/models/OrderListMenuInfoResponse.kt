package com.softsquared.template.kotlin.src.main.orderList.models

import java.io.Serializable

data class OrderListMenuInfoResponse(
    val cartIdx: Int,
    val orderCount: Int,
    val menuName: String,
    val menuOptions: String,
    val isGood: String,
    val mulPrice: Int
) : Serializable
