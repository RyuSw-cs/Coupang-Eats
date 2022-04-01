package com.softsquared.template.kotlin.src.main.location.search.models

import com.google.gson.annotations.SerializedName

data class SearchResultResponse(
    @SerializedName("meta")
    val meta: SearchMetaResponse,
    @SerializedName("documents")
    val documents: List<SearchDocumentsResponse>
)