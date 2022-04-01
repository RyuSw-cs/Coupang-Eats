package com.softsquared.template.kotlin.src.main.storeInfo

import com.softsquared.template.kotlin.src.main.storeInfo.models.StoreInfoResponse

interface StoreInfoView {
    fun onGetStoreInfoSuccess(response: StoreInfoResponse)
    fun onGetStoreInfoFailure(error: String)
}