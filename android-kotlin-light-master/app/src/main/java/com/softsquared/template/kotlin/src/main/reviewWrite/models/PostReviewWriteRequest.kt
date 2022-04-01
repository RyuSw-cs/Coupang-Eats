package com.softsquared.template.kotlin.src.main.reviewWrite.models

import okhttp3.MultipartBody
import retrofit2.http.Multipart

data class PostReviewWriteRequest(
    val userOrderIdx: Int,
    val score: Int,
    val reasonForStore: String,
    val content: String,
    val isMenuGood: Map<Int, String>,
    val reasonForMenu: Map<Int, String>,
    val isDeliveryManGood: String,
    val reasonForDelivery:String,
    val image: List<String>
)
