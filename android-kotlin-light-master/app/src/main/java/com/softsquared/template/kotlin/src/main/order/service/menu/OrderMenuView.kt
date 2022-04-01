package com.softsquared.template.kotlin.src.main.order.service.menu

import com.softsquared.template.kotlin.src.main.storeInfo.models.StoreInfoResponse

interface OrderMenuView {
    fun onGetOrderMenuSuccess(response: StoreInfoResponse)
    fun onGetOrderMenuFailure(error:String)
}