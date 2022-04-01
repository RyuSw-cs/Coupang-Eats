package com.softsquared.template.kotlin.src.main.review.models

import com.softsquared.template.kotlin.config.BaseResponse

data class ReviewResponse(
    val result: List<ReviewDetailResponse>
) : BaseResponse()
