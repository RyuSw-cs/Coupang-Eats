package com.softsquared.template.kotlin.src.main.reviewWrite

import com.softsquared.template.kotlin.src.main.reviewWrite.models.*
import retrofit2.Call
import retrofit2.http.*

interface ReviewWriteRetrofitInstance {
    @POST("stores/review/new")
    fun postReviewWrite(
        @Body body: PostReviewWriteRequest
    ): Call<PostReviewWriteResponse>

    @PUT("stores/review")
    fun putReviewWrite(
        @Body body: PutReviewWriteRequest
    ): Call<PutReviewWriteResponse>

    @PATCH("stores/review/deletion")
    fun deleteReviewWrite(
        @Body body: DeleteReviewWriteRequest
    ): Call<DeleteReviewWriteResponse>
}