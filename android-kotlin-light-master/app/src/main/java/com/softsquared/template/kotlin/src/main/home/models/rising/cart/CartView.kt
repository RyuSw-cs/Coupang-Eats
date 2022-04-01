package com.softsquared.template.kotlin.src.main.home.models.rising.cart

import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartGetResponse

interface CartView {
    fun onGetStoreInfoMenuCartSuccess(response: StoreInfoMenuCartGetResponse)
    fun onGetPostStoreInfoMenuCartFailure(error:String)
}