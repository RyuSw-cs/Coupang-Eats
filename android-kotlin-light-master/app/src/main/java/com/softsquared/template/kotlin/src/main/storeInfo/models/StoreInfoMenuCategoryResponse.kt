package com.softsquared.template.kotlin.src.main.storeInfo.models

data class StoreInfoMenuCategoryResponse(
    val categoryName: String,
    val menuDetail: List<StoreInfoMenuDetailResponse>
)
