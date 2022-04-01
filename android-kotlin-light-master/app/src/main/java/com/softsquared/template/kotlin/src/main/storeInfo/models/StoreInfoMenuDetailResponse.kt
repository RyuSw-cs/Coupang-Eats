package com.softsquared.template.kotlin.src.main.storeInfo.models

import java.io.Serializable

data class StoreInfoMenuDetailResponse(
    val menuIdx: Int,
    val menuName: String,
    val menuPrice: Int,
    val menuDetail: String,
    val menuImgUrl: String,
    val isOption: String,
    val status: String,
    val isManyOrder:String,
    val isManyReview:String
):Serializable