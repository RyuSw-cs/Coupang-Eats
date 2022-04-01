package com.softsquared.template.kotlin.src.main.storeInfo.menu.models

import java.io.Serializable

data class StoreInfoMenuOptionResponse(
    val optionsTitle: String,
    val isRequired: String,
    val choiceCount: Int,
    val menuOptionsDetail: List<StoreInfoMenuDetailOptionResponse>
): Serializable
