package com.softsquared.template.kotlin.src.main.location

import com.softsquared.template.kotlin.src.main.home.models.rising.address.models.HomeAddressResponse

interface LocationView {
    fun onGetLocationSuccess(response:HomeAddressResponse)
    fun onGetLocationFailure(error:String)
}