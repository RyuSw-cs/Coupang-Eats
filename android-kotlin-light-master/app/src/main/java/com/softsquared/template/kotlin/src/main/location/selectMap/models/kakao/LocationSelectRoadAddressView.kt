package com.softsquared.template.kotlin.src.main.location.selectMap.models.kakao

import com.softsquared.template.kotlin.src.main.location.selectMap.models.kakao.models.LocationSelectAddressResponse
import com.softsquared.template.kotlin.src.main.location.selectMap.models.kakao.models.LocationSelectResultResponse

interface LocationSelectRoadAddressView {
    fun onGetLocationSuccess(data: LocationSelectResultResponse)

    fun onGetLocationFailure(message: String)

    fun onGetLocationSuccessWithoutRoadAddress(data : LocationSelectAddressResponse)
}