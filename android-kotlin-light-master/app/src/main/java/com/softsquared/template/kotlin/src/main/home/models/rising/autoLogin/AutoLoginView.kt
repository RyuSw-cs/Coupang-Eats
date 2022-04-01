package com.softsquared.template.kotlin.src.main.home.models.rising.autoLogin

interface AutoLoginView {
    fun onGetAutoLoginSuccess(response: AutoLoginResponse)
    fun onGetAutoLoginFailure(error: String)
    fun onGetAutoLoginRefresh(code:Int)
}