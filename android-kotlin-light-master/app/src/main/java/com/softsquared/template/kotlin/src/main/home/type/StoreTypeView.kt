package com.softsquared.template.kotlin.src.main.home.type

import com.softsquared.template.kotlin.src.main.home.models.rising.store.models.HomeStoreInfoResponse
import com.softsquared.template.kotlin.src.main.home.type.models.HomeStoreGetTypeResponse

interface StoreTypeView {
    fun onGetStoreTypeInfoSuccess(response: HomeStoreGetTypeResponse)
    fun onGetStoreTypeInfoFailure(error : String)
}