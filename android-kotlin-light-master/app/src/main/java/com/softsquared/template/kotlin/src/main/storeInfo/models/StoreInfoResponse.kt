package com.softsquared.template.kotlin.src.main.storeInfo.models

import com.softsquared.template.kotlin.config.BaseResponse

data class StoreInfoResponse(
    val result: StoreInfoDetailResponse
) : BaseResponse()
