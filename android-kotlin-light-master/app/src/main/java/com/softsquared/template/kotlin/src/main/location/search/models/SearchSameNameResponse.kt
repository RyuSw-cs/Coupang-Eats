package com.softsquared.template.kotlin.src.main.location.search.models

import com.google.gson.annotations.SerializedName

data class SearchSameNameResponse(
    @SerializedName("region")
    val region: List<String>,
    @SerializedName("keyword")
    val keyword: String,
    @SerializedName("selected_region")
    val selectedRegion: String
)
