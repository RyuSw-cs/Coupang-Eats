package com.softsquared.template.kotlin.src.main.myPage

import com.softsquared.template.kotlin.src.main.myPage.models.MyEatsResponse

interface MyPageView {
    fun onGetMyEatsSuccess(response: MyEatsResponse)
    fun onGetMyEatsFailure(error: String)
}