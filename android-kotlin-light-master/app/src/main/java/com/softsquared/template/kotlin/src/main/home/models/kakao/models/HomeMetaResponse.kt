package com.softsquared.template.kotlin.src.main.home.models.kakao.models

import com.google.gson.annotations.SerializedName

data class HomeMetaResponse(
    @SerializedName("total_count")
    val totalCount: Int
)
