package com.softsquared.template.kotlin.src.main.review

import com.softsquared.template.kotlin.src.main.review.models.ReviewResponse

interface ReviewActivityView {
    fun onGetReviewSuccess(response: ReviewResponse)
    fun onGetReviewFailure(error: String)
}