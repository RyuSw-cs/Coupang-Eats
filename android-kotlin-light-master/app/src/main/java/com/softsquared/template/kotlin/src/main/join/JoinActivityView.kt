package com.softsquared.template.kotlin.src.main.join

import com.softsquared.template.kotlin.src.main.join.models.JoinResponse

interface JoinActivityView {
    fun onPostJoinSuccess(data: JoinResponse)
    fun onPostJoinFailure(error: String)
}