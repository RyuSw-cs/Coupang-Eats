package com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models

import com.softsquared.template.kotlin.src.main.home.models.rising.address.models.HomeAddressModel
import com.softsquared.template.kotlin.src.main.storeInfo.models.StoreInfoDeliveryResponse
import java.io.Serializable

data class StoreInfoMenuCartGetDataResponse(
    val nowAddress: HomeAddressModel,
    val storeIdx: Int,
    val storeName: String,
    val isCheetah: String,
    val minimumPrice: Int,
    val timeDelivery: String,
    val timeToGo: String,
    val storeLongitude: Double,
    val storeLatitude: Double,
    val isToGo: String,
    val isCoupon: String,
    val status: String,
    val buildingName: String,
    val storeAddress: String,
    val storeAddressDetail: String,
    val distance: Double,
    val totalPrice: Int,
    val deliveryFeeList: List<StoreInfoMenuCartDeliveryResponse>,
    val cartMenu: List<StoreInfoMenuCartInfoResponse>
) : Serializable

