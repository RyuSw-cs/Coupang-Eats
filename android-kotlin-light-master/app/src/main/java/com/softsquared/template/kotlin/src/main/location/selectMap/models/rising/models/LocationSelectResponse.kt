package com.softsquared.template.kotlin.src.main.location.selectMap.models.rising.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class LocationSelectResponse(
    @SerializedName("result")
    val result : LocationSelectLatLon
):BaseResponse()