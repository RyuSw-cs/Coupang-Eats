package com.softsquared.template.kotlin.src.main.orderList.models

import com.softsquared.template.kotlin.config.BaseResponse

data class OrderListResponse(
    val result : List<OrderListDetailResponse>
):BaseResponse()
