package com.softsquared.template.kotlin.src.main.location.selectMap.models.kakao.models

import com.google.gson.annotations.SerializedName

data class LocationSelectMetaResponse(
    @SerializedName("total_count")
    val totalCount: Int
)
