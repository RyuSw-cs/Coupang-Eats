package com.softsquared.template.kotlin.src.main.favorite.models

data class FavoriteDetailResponse(
    val storeIdx: Int,
    val storeImgUrl: String,
    val storeName: String,
    val isCheetah: String,
    val timeDelivery: String,
    val isToGo: String,
    val isCoupon: String,
    val distance: Double,
    val reviewCount: Int,
    val reviewScore: Double,
    val deliveryFee: String,
    val storeBestCoupon: String,
    val isNewStore: String,
    val businessStatus:String,
)