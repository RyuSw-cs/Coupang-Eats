package com.softsquared.template.kotlin.src.main.location.selectMap.models.rising

import com.softsquared.template.kotlin.src.main.location.selectMap.models.rising.models.LocationSelectResponse

interface LocationSelectView {
    fun postLocationSuccess(data: LocationSelectResponse)
    fun postLocationFailure(message:String)
}