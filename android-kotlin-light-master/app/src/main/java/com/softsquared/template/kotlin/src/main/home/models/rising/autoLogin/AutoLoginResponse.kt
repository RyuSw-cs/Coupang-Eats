package com.softsquared.template.kotlin.src.main.home.models.rising.autoLogin

import com.softsquared.template.kotlin.config.BaseResponse

data class AutoLoginResponse(
    val result: AutoLoginDetailResponse
) : BaseResponse()