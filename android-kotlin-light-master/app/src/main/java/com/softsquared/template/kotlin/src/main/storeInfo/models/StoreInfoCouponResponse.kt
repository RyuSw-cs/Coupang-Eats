package com.softsquared.template.kotlin.src.main.storeInfo.models

data class StoreInfoCouponResponse(
    val couponIdx: Int,
    val storeInx: Int,
    val couponTitle: String,
    val discountPrice: String,
    val limitPrice: String,
    val endDate: String,
    val couponType: String,
    val isUserOwn : Int
)
