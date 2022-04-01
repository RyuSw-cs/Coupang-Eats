package com.softsquared.template.kotlin.src.main.order.service.cart

import com.softsquared.template.kotlin.src.main.order.service.cart.models.OrderCartDeleteResponse
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartGetResponse
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartPostResponse

interface OrderCartView {
    fun onGetOrderCartSuccess(response: StoreInfoMenuCartGetResponse)
    fun onGetOrderCartFailure(error: String)

    fun onPostOrderMenuSuccess(response: StoreInfoMenuCartPostResponse)
    fun onPostOrderMenuFailure(error: String)

    fun onDeleteOrderMenuSuccess(response: OrderCartDeleteResponse)
    fun onDeleteOrderMenuFailure(error: String)
}