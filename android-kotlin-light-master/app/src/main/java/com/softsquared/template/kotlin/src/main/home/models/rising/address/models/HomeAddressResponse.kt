package com.softsquared.template.kotlin.src.main.home.models.rising.address.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse
import java.io.Serializable

data class HomeAddressResponse(
    @SerializedName("result")
    val result: HomeAddressResultResponse
) : BaseResponse(), Serializable
