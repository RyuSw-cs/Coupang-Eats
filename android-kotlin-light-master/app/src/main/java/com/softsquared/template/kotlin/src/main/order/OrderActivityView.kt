package com.softsquared.template.kotlin.src.main.order

import com.softsquared.template.kotlin.src.main.order.models.OrderDeliveryResponse
import com.softsquared.template.kotlin.src.main.order.models.OrderModifyResponse

interface OrderActivityView {
    fun onPostOrderSuccess(response: OrderDeliveryResponse)
    fun onPostOrderFailure(error: String)

    fun onPutOrderSuccess(response:OrderModifyResponse)
    fun onPutOrderFailure(error: String)
}