package com.softsquared.template.kotlin.src.main.location.search.models

import com.google.gson.annotations.SerializedName

data class SearchMetaResponse(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("pageable_count")
    val pageableCount: Int,
    @SerializedName("is_end")
    val isEnd: Boolean,
    @SerializedName("same_name")
    val sameName: SearchSameNameResponse
)
