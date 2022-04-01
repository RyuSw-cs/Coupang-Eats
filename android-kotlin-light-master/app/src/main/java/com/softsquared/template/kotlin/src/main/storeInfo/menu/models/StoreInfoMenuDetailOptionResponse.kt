package com.softsquared.template.kotlin.src.main.storeInfo.menu.models

import java.io.Serializable

data class StoreInfoMenuDetailOptionResponse(
    val optionsContent: String,
    val addPrice: Int
):Serializable