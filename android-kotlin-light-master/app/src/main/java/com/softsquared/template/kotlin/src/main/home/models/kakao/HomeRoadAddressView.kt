package com.softsquared.template.kotlin.src.main.home.models.kakao

import com.softsquared.template.kotlin.src.main.home.models.kakao.models.HomeLocationResultResponse

interface HomeRoadAddressView {
    fun onGetLocationSuccess(data: HomeLocationResultResponse)

    fun onGetLocationFailure(message: String)
}