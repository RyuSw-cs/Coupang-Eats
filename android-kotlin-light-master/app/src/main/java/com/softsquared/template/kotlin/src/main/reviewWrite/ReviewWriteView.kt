package com.softsquared.template.kotlin.src.main.reviewWrite

import com.softsquared.template.kotlin.src.main.reviewWrite.models.DeleteReviewWriteResponse
import com.softsquared.template.kotlin.src.main.reviewWrite.models.PostReviewWriteResponse
import com.softsquared.template.kotlin.src.main.reviewWrite.models.PutReviewWriteResponse

interface ReviewWriteView {
    fun onPostReviewWriteSuccess(response: PostReviewWriteResponse)
    fun onPostReviewWriteFailure(error: String)

    fun onPutReviewWriteSuccess(response: PutReviewWriteResponse)
    fun onPutReviewWriteFailure(error: String)

    fun onDeleteReviewWriteSuccess(response: DeleteReviewWriteResponse)
    fun onDeleteReviewWriteFailure(error: String)
}