package com.softsquared.template.kotlin.src.main.home.models.rising.store.models

import com.softsquared.template.kotlin.config.BaseResponse

data class HomeStoreResponse(
    val result: List<HomeStoreInfoResponse>
) : BaseResponse()