package com.softsquared.template.kotlin.src.main.storeInfo.menu.models

import com.softsquared.template.kotlin.config.BaseResponse

data class StoreInfoMenuResponse(
    val result:StoreInfoMenuDetailResponse
):BaseResponse()
