package com.softsquared.template.kotlin.src.main.order.service.address

import com.softsquared.template.kotlin.src.main.home.models.rising.address.models.HomeAddressResponse

interface OrderAddressView {
    fun onGetOrderAddressSuccess(response:HomeAddressResponse)
    fun onGetOrderAddressFailure(error:String)
}