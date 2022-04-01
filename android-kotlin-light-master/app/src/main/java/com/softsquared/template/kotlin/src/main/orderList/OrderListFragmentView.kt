package com.softsquared.template.kotlin.src.main.orderList

import com.softsquared.template.kotlin.src.main.orderList.models.OrderListResponse

interface OrderListFragmentView {
    fun onGetOrderListSuccess(response: OrderListResponse)
    fun onGetOrderListFailure(error: String)
}