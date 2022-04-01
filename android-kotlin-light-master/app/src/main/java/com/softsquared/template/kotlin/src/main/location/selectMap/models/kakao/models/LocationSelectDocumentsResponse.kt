package com.softsquared.template.kotlin.src.main.location.selectMap.models.kakao.models

import com.google.gson.annotations.SerializedName

data class LocationSelectDocumentsResponse(
    @SerializedName("road_address")
    val locationSelectRoadAddress: LocationSelectRoadAddressResponse,

    @SerializedName("address")
    val locationSelectAddress: LocationSelectAddressResponse
)
