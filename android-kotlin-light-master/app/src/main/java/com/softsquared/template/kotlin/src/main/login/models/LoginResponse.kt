package com.softsquared.template.kotlin.src.main.login.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class LoginResponse(
    @SerializedName("result")
    val result : LoginResultResponse
):BaseResponse()