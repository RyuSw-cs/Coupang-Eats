package com.softsquared.template.kotlin.src.main.storeInfo.models

data class StoreInfoPhotoReviewResponse(
    val reviewIdx: Int,
    val reviewImgUrl: String,
    val content: String,
    val score: Int,
    val createdAt: String
)
