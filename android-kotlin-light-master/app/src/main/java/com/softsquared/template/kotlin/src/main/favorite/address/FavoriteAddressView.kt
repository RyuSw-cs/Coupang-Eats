package com.softsquared.template.kotlin.src.main.favorite.address

import com.softsquared.template.kotlin.src.main.home.models.rising.address.models.HomeAddressResponse

interface FavoriteAddressView {
    fun onGetAddressSuccess(response:HomeAddressResponse)
    fun onGetAddressFailure(error:String)
}