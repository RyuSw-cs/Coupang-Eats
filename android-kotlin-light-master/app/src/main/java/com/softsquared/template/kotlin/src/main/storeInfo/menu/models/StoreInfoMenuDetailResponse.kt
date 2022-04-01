package com.softsquared.template.kotlin.src.main.storeInfo.menu.models

import java.io.Serializable

data class StoreInfoMenuDetailResponse(
    val menuIdx : Int,
    val menuImgUrl: List<String>,
    val menuName: String,
    val menuDetail: String,
    var menuPrice: Int,
    val menuOptions: List<StoreInfoMenuOptionResponse>
): Serializable