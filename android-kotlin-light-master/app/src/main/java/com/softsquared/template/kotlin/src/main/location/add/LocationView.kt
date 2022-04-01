package com.softsquared.template.kotlin.src.main.location.add

import com.softsquared.template.kotlin.src.main.location.add.models.LocationAddResponse
import com.softsquared.template.kotlin.src.main.location.add.models.LocationModifyResponse

interface LocationView {
    fun onPostLocationSuccess(data: LocationAddResponse)
    fun onPostLocationFailure(error: String)

    fun onPutLocationSuccess(data : LocationModifyResponse)
    fun onPutLocationFailure(error: String)
}