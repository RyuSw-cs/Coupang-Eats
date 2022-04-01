package com.softsquared.template.kotlin.src.main.storeInfo.menu

import com.softsquared.template.kotlin.src.main.storeInfo.menu.models.StoreInfoMenuResponse

interface StoreInfoMenuView {
    fun onGetStoreInfoMenuSuccess(response: StoreInfoMenuResponse)
    fun onGetStoreInfoMenuFailure(error: String)
}