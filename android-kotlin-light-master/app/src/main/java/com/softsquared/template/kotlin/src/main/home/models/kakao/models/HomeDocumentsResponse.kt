package com.softsquared.template.kotlin.src.main.home.models.kakao.models

import com.google.gson.annotations.SerializedName

data class HomeDocumentsResponse(
    @SerializedName("road_address")
    var homeRoadAddress: HomeRoadAddressResponse?,

    @SerializedName("address")
    var homeOldAddress: HomeOldAddressResponse?
)
