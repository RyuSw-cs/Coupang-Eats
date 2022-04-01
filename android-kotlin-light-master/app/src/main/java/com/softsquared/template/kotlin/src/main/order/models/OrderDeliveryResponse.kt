package com.softsquared.template.kotlin.src.main.order.models

import com.softsquared.template.kotlin.config.BaseResponse

data class OrderDeliveryResponse(
    val result: OrderDeliverIdxResponse
) : BaseResponse()
