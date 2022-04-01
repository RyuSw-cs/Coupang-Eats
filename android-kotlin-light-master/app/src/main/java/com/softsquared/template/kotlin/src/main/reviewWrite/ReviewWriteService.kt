package com.softsquared.template.kotlin.src.main.reviewWrite

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.reviewWrite.models.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class ReviewWriteService(val view: ReviewWriteView) {
    fun tryPostReviewWrite(
        userOrderIdx: Int,
        score: Int,
        reasonForStore: String,
        content: String,
        isMenuGood: Map<Int, String>,
        reasonForMenu: Map<Int, String>,
        isDeliveryGood: String,
        reasonForDelivery: String,
        image: List<String>
    ) {
        val reviewWriteRetrofitInstance =
            ApplicationClass.sRetrofit.create(ReviewWriteRetrofitInstance::class.java)
        reviewWriteRetrofitInstance.postReviewWrite(
            PostReviewWriteRequest(
                userOrderIdx,
                score,
                reasonForStore,
                content,
                isMenuGood,
                reasonForMenu,
                isDeliveryGood,
                reasonForDelivery,
                image
            )
        ).enqueue(object : Callback<PostReviewWriteResponse> {
            override fun onResponse(
                call: Call<PostReviewWriteResponse>,
                response: Response<PostReviewWriteResponse>
            ) {
                if (response.body()?.isSuccess == true) {
                    view.onPostReviewWriteSuccess(response.body() as PostReviewWriteResponse)
                } else {
                    view.onPostReviewWriteFailure(response.body()?.message!!)
                }
            }

            override fun onFailure(call: Call<PostReviewWriteResponse>, t: Throwable) {
                view.onPostReviewWriteFailure(t.message ?: "리뷰 등록 실패")
            }
        })
    }

    fun tryPutReviewWrite(
        userOrderIdx: Int,
        score: Int,
        reasonForStore: String,
        content: String,
        isMenuGood: Map<Int, String>,
        reasonForMenu: Map<Int, String>,
        isDeliveryGood: String,
        reasonForDelivery: String,
        image: List<String>
    ) {
        val reviewWriteRetrofitInstance =
            ApplicationClass.sRetrofit.create(ReviewWriteRetrofitInstance::class.java)
        reviewWriteRetrofitInstance.putReviewWrite(
            PutReviewWriteRequest(
                userOrderIdx,
                score,
                reasonForStore,
                content,
                isMenuGood,
                reasonForMenu,
                isDeliveryGood,
                reasonForDelivery,
                image
            )
        ).enqueue(object : Callback<PutReviewWriteResponse> {
            override fun onResponse(
                call: Call<PutReviewWriteResponse>,
                response: Response<PutReviewWriteResponse>
            ) {
                if (response.body()?.isSuccess == true) {
                    view.onPutReviewWriteSuccess(response.body() as PutReviewWriteResponse)
                } else {
                    view.onPutReviewWriteFailure(response.body()?.message!!)
                }
            }

            override fun onFailure(call: Call<PutReviewWriteResponse>, t: Throwable) {
                view.onPutReviewWriteFailure(t.message ?: "리뷰 수정 실패")
            }
        })
    }

    fun tryDeleteReviewWrite(userOrderIdx: Int) {
        val reviewWriteRetrofitInstance =
            ApplicationClass.sRetrofit.create(ReviewWriteRetrofitInstance::class.java)
        reviewWriteRetrofitInstance.deleteReviewWrite(DeleteReviewWriteRequest(userOrderIdx))
            .enqueue(object : Callback<DeleteReviewWriteResponse> {
                override fun onResponse(
                    call: Call<DeleteReviewWriteResponse>,
                    response: Response<DeleteReviewWriteResponse>
                ) {
                    if (response.body()?.isSuccess == true) {
                        view.onDeleteReviewWriteSuccess(response.body() as DeleteReviewWriteResponse)
                    } else {
                        view.onDeleteReviewWriteFailure(response.body()?.message!!)
                    }
                }

                override fun onFailure(call: Call<DeleteReviewWriteResponse>, t: Throwable) {
                    view.onDeleteReviewWriteFailure(t.message ?: "리뷰 삭제 실패")
                }

            })
    }
}