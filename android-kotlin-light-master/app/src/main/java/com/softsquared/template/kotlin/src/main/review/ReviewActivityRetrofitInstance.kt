package com.softsquared.template.kotlin.src.main.review

import com.softsquared.template.kotlin.src.main.review.models.ReviewResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ReviewActivityRetrofitInstance {
    @GET("stores/review-list")
    fun getReview(
        @Query("storeIdx") storeIdx: Int,
        @Query("sort")sort:String,
        @Query("isPhoto")isPhoto:String
    ): Call<ReviewResponse>
}