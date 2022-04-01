package com.softsquared.template.kotlin.src.main.home.models.kakao.models

import com.google.gson.annotations.SerializedName

data class HomeLocationResultResponse(
    @SerializedName("meta")
    val homeMeta: HomeMetaResponse,

    @SerializedName("documents")
    val documents: List<HomeDocumentsResponse>
)
