package com.softsquared.template.kotlin.src.main.reviewWrite

import android.os.Bundle
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityReviewWriteBinding

class ReviewWriteActivity :
    BaseActivity<ActivityReviewWriteBinding>(ActivityReviewWriteBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    fun init() {

    }
}