package com.softsquared.template.kotlin.src.main.home.models.rising.store.models

import java.io.Serializable

data class HomeStoreBestCouponResponse(
    val couponIdx: Int,
    val maxDiscountPrice: String,
    val couponType: String
): Serializable

