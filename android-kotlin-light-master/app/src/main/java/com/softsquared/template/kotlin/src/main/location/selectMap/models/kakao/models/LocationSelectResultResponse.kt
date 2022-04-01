package com.softsquared.template.kotlin.src.main.location.selectMap.models.kakao.models

import com.google.gson.annotations.SerializedName

data class LocationSelectResultResponse(
    @SerializedName("meta")
    val locationSelectMeta: LocationSelectMetaResponse,

    @SerializedName("documents")
    val documents: List<LocationSelectDocumentsResponse>
)
