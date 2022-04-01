package com.softsquared.template.kotlin.src.main.storeInfo.models

data class StoreInfoDetailResponse(
    val storeIdx: Int,
    val storeImgUrl: List<String>,
    val storeName: String,
    val isCheetah: String,
    val timeDelivery: String,
    val isToGo: String,
    val isCoupon: String,
    val minimumPrice: Int,
    val buildingName : String,
    val storeAddress : String,
    val storeAddressDetail : String,
    val storeLongitude:Double,
    val storeLatitude:Double,
    val distance:Double,
    val status: String,
    val reviewScore: Double,
    val reviewCount: Int,
    val timeToGo: String,
    val isFavoriteStore : String,
    val minimumDeliveryFee : Int,

    val storeCouponInfo: List<StoreInfoCouponResponse>,
    val deliveryFeeInfo: List<StoreInfoDeliveryResponse>,
    val photoReview: List<StoreInfoPhotoReviewResponse>,
    val menuCategory: List<StoreInfoMenuCategoryResponse>
)
