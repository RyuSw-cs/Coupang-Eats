package com.softsquared.template.kotlin.src.main.login

import com.softsquared.template.kotlin.src.main.login.models.LoginResponse

interface LoginActivityView {
    fun postLoginSuccess(data: LoginResponse)
    fun postLoginFailure(message: String)
}