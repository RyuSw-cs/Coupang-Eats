package com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models

import com.softsquared.template.kotlin.config.BaseResponse
import java.io.Serializable

data class StoreInfoMenuCartGetResponse(
    val result: StoreInfoMenuCartGetDataResponse
) : BaseResponse(), Serializable
