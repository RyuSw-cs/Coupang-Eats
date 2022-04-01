package com.softsquared.template.kotlin.src.main.home.models.rising.store.models

import java.io.Serializable

data class HomeStoreInfoResponse(
    val storeInfo: HomeStoreDetailResponse,
    val storeBestCoupon: HomeStoreBestCouponResponse,
):Serializable
