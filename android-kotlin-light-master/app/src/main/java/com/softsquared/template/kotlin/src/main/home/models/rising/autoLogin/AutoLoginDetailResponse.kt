package com.softsquared.template.kotlin.src.main.home.models.rising.autoLogin

data class AutoLoginDetailResponse(
    val userIdx:String,
    val jwt:String,
    val refreshToken:String
)
