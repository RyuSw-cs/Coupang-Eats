package com.softsquared.template.kotlin.src.main.review.models

data class ReviewDetailResponse(
    val reviewUserName:String,
    val reviewIdx:Int,
    val score:Int,
    val uploadDate:String,
    val createdAt:String,
    val content:String,
    val orderMenuListString : String,
    val reviewImg:List<String>,
    val bossReview:ReviewBossResponse,
    val helpedCount:Int,
    val isMyHelped:String,
    val isMyReview:String,
    val isPhotoReview:String
)
