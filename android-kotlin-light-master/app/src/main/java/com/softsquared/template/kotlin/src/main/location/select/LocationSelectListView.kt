package com.softsquared.template.kotlin.src.main.location.select

import com.softsquared.template.kotlin.src.main.location.add.models.LocationModifyResponse

interface LocationSelectListView {
    fun onPutLocationSuccess(data : LocationModifyResponse)
    fun onPutLocationFailure(error: String)
}