package com.softsquared.template.kotlin.src.main.review

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.review.models.ReviewResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewActivityService(val view: ReviewActivityView) {
    fun tryGetReview(storeIdx: Int, sort: String, isPhoto: String) {
        val reviewActivityRetrofitInstance =
            ApplicationClass.sRetrofit.create(ReviewActivityRetrofitInstance::class.java)
        reviewActivityRetrofitInstance.getReview(storeIdx, sort, isPhoto)
            .enqueue(object : Callback<ReviewResponse> {
                override fun onResponse(
                    call: Call<ReviewResponse>,
                    response: Response<ReviewResponse>
                ) {
                    if (response.body()?.isSuccess == true) {
                        view.onGetReviewSuccess(response.body() as ReviewResponse)
                    } else {
                        view.onGetReviewFailure(response.body()?.message!!)
                    }
                }

                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                    view.onGetReviewFailure(t.message ?: "리뷰 가져오기 실패")
                }
            })
    }
}