package com.softsquared.template.kotlin.src.main.orderList.models

import com.softsquared.template.kotlin.src.main.home.models.rising.address.models.HomeAddressModel
import com.softsquared.template.kotlin.src.main.home.models.rising.address.models.HomeAddressResponse
import com.softsquared.template.kotlin.src.main.location.add.models.LocationModifyResponse
import java.io.Serializable

data class OrderListDetailResponse(
    val userDeliveryAddress: HomeAddressModel,
    val userOrderIdx:Int,
    val storeIdx:Int,
    val storeImgUrl:String,
    val storeName:String,
    val orderTime:String,
    val status:String,
    val totalPrice:String,
    val orderMenuInfo: List<OrderListMenuInfoResponse>,
    val reviewScore:Int,
    val deliveryFee:Int,
    val businessStatus:String
):Serializable
