package com.softsquared.template.kotlin.src.main.location.add.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class LocationAddResponse(
    @SerializedName("result")
    val result: LocationAddResultResponse
) : BaseResponse()