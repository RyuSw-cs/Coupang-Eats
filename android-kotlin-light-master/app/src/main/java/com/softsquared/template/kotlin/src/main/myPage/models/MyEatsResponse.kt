package com.softsquared.template.kotlin.src.main.myPage.models

import com.softsquared.template.kotlin.config.BaseResponse

data class MyEatsResponse(
    val result: MyEatsDetailResponse
) : BaseResponse()
