package com.softsquared.template.kotlin.src.main.home.models.rising.address

import com.softsquared.template.kotlin.src.main.home.models.rising.address.models.HomeAddressResponse

interface HomeAddressView {
    fun getAddressSuccess(data: HomeAddressResponse)
    fun getAddressFailure(error: String)
}