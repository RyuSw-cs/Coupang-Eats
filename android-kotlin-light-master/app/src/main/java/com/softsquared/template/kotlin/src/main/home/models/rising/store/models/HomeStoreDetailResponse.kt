package com.softsquared.template.kotlin.src.main.home.models.rising.store.models

import java.io.Serializable

data class HomeStoreDetailResponse(
    val storeIdx: Int,
    val storeImgUrl: List<String>,
    val storeName: String,
    val isCheetah: String,
    val timeDelivery: String,
    val isToGo: String,
    val isCoupon: String,
    val minimumPrice: Int,
    val buildingName: String,
    val storeAddress: String,
    val storeAddressDetail: String,
    val status: String,
    val createdAt: String,
    val reviewScore: Double,
    val reviewCount: Int,
    val orderCount: Int,
    val distance: Double,
    val deliveryFee: String,
    val isNewStore:String
) : Serializable